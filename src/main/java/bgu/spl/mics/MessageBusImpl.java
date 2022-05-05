package bgu.spl.mics;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	//manage the microServices and their events
	private HashMap<MicroService,LinkedList<Message>> microMap;

	//manage the messages and their microServices
	private HashMap<Class<? extends Message>,LinkedList<MicroService>> messageMap;
	
	//manage the future-messages links
	private HashMap<Message, Future> ftMap;

	private MessageBusImpl() {
		microMap = new HashMap<MicroService,LinkedList<Message>>();
		messageMap = new HashMap<Class<? extends Message>,LinkedList<MicroService>>();
		ftMap = new HashMap<Message, Future>();
	}

	public static MessageBusImpl getInstance(){
		return new MessageBusImpl();
	}


	//here we mainly maintain the MessageMap HashMap
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {

		//if this is the first event of its kind (type)-create new
		synchronized (messageMap) {
			messageMap.computeIfAbsent(type, k -> new LinkedList<MicroService>());
		}

		//now we add the microService to the queue and notify all the MCs of the same type
		synchronized (messageMap.get(type)) {
			messageMap.get(type).add(m);

			//here, we notify all those who are sub'd to this event, so that they can "awake" from their wait if needed
			messageMap.get(type).notifyAll();
		}

		//now we notify all that the message map is now open for all
		synchronized (messageMap) {
			messageMap.notifyAll();
		}
	}

	//almost identical to event
	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized (messageMap) {
			messageMap.computeIfAbsent(type, k -> new LinkedList<MicroService>());
		}
		synchronized (messageMap.get(type)) {
			messageMap.get(type).add(m);
		}
		synchronized (messageMap) {
			messageMap.notifyAll();
		}
	}

	//we both remove AND resolve the Future according to the result given
	@Override
	public <T> void complete(Event<T> e, T result) {
		ftMap.remove(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		synchronized (messageMap){
			synchronized (b.getClass()){

				//first we make sure that there are MicroServices available to "listen"
				while(microMap.get(b.getClass()) == null) {
					try {
						microMap.wait();
					} catch (InterruptedException interruptedException) {
						interruptedException.printStackTrace();
					}
				}
			}

			//now we add the broadcast to all the microservices subscribed
			int listeners = microMap.get(b.getClass()).size();
			for (int k=0;k<listeners;k++){
				//first we get a hold on the relevant microservice
				MicroService mic = messageMap.get(b.getClass()).get(k);


				//now we add the broadcast to it's queue of messages
				synchronized (microMap){
					microMap.get(mic).add(b);
					microMap.notifyAll();
				}
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {

		//we make sure no one will change all the relevant cells, both in messageMap
		//in general, and all the microservices sub'd to this event
		synchronized (e.getClass()) {
			synchronized (messageMap) {
				//first we make sure that there are microservices sub'd "listening" to this event
				while (microMap.get(e.getClass()) == null || microMap.get(e.getClass()).isEmpty()) {
					try {
						microMap.wait();
					} catch (InterruptedException interruptedException) {
						interruptedException.printStackTrace();
					}
				}
			}
		}

		//now we allocate the message to a matching future and microservice
		synchronized (messageMap.get(e.getClass())) {
//			synchronized (microMap) { 	//is this too much to block ??????????
			//first we both find and remove the first microservice in the queue
			//that sub'd to this event
			MicroService mic = messageMap.get(e.getClass()).remove();

			//now that we found him, we put him at the back of the queue, so that
			//we will not damage the RoundRobin-matter, as we were instructed
			messageMap.get(e.getClass()).add(mic);


			//we allocate the event to this microservice
			synchronized (microMap) {
				microMap.get(mic).add(e);
				microMap.notifyAll();
			}
			synchronized (microMap.get(mic)) {


				//we create a future, that will hold the result once the event is resolved
				Future nft = new Future();

				//we put it in the future map so we can keep track on it
				ftMap.put(e, nft);
				//we notify all that we are done
				microMap.get(mic).notifyAll();
				return nft;
			}
		}
	}
//	}


	//here all we do is create a new spot for the microservice in the microMap
	@Override
	public void register(MicroService m) {
		synchronized (microMap){
			microMap.put(m,new LinkedList<Message>());
		}
	}


	//here we remove m from all the broadcasts/events to which m was sub'd to
	// (in messageMap and microMap)
	@Override
	public void unregister(MicroService m) {
		synchronized (microMap) {
			microMap.remove(m);
		}
		synchronized (messageMap){
			messageMap.forEach((k,v)->{
				if(v.contains(m))
					v.remove(m);
			});
		}
	}



	//here all we do is wait for a message, and once received, send the microservice
	//back to its sender, so it will deal with the msg
	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		if(microMap.get(m)==null){throw new InterruptedException("there is no such microserive registered");}
		synchronized (microMap.get(m)){
			//here we wait someone will enter a msg to the queue in
			//micromap.get(m) - so we act only when it's not empty
			while (microMap.get(m).isEmpty()){microMap.get(m).wait();}

			//we also remove it so there will be no duplications
			return microMap.get(m).remove();
		}
	}

	public <T> boolean checkEventSub(Event<T> e, MicroService m){
		return false;               //needs to implement
	}

	public boolean checkBroadSub(Broadcast b, MicroService m){
		return false;               //needs to implement
	}

	public <T> boolean checkMicroEvent(Event<T> e, MicroService m){
		return false;                //needs to implement
	}

	public boolean checkMicroBroad(Broadcast b, MicroService m){
		return false;                   //needs to implement
	}

	public boolean hasFuture(Message m){
		return false;                    //needs to implement
	}

	public boolean isRegistered(MicroService m){
		return false;                     //needs to implement
	}


}
