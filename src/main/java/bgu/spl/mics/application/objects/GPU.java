package bgu.spl.mics.application.objects;

import java.util.LinkedList;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model;
    private Cluster cluster;
    private int time;
    private LinkedList<DataBatch> pd,upd;

    public GPU() {
        model= null;
        cluster=null;
        time=0;
        pd=new LinkedList<>();
        upd=new LinkedList<>();
        type = null;
    }

    public GPU(Type type, Model model, Cluster cluster){
        this.type=type;
        this.cluster=cluster;
        this.model=model;
        pd=new LinkedList<DataBatch>();
        upd=new LinkedList<DataBatch>();
        time = 0;
    }

    /**
     * @return return type
     */
    public Type getType() {return type;}

    /**
     * @param type Type - new type to set
     * @pre no condition
     * @post this.type==type
     */
    public void setType(Type type) {this.type = type;}

    /**
     * @return return cluster
     */
    public Cluster getCluster() {return cluster;}

    /**
     * @param cluster Cluster - new cluster to set
     * @pre no condition
     * @post this.cluster==cluster
     */
    public void setCluster(Cluster cluster) {this.cluster = cluster;}

    /**
     * @return return model
     */
    public Model getModel() {return model;}

    /**
     * @param model Model - new model to set
     * @pre no condition
     * @post this.model==model
     */
    public void setModel(Model model) {this.model = model;}

    /**
     * @return return time
     */
    public int getTime() { return time; }

    /**
     * @param time int - new time to set
     * @pre no condition
     * @post this.time==time
     */
    public void setTime(int time) { this.time = time; }

    /**
     * @return return pd
     */
    public LinkedList<DataBatch> getPd() { return pd; }

    /**
     * @param pd LinkedList<DataBatch> - new list to set
     * @pre no condition
     * @post this.pd==pd
     */
    public void setPd(LinkedList<DataBatch> pd) { this.pd = pd; }

    /**
     * @return return upd
     */
    public LinkedList<DataBatch> getUpd() { return upd; }

    /**
     * @param upd LinkedList<DataBatch> - new list to set
     * @pre no condition
     * @post this.upd==upd
     */
    public void setUpd(LinkedList<DataBatch> upd) { this.upd = upd; }

    /**
     * @param data DataBatch - new data to add to the pd
     * @pre no condition
     * @inv will be inserted if and only if there's enough room
     * @post pd.size()++
     */
    public boolean insert(DataBatch data){
        switch (getType()){
            case GTX1080: if(pd.size()==8) {return false;}
            case RTX2080: if(pd.size()==16) {return false;}
            case RTX3090: if(pd.size()==32) {return false;}
        }
        pd.add(data);
        return true;
    }

    /**
     * @param db DataBatch - send this unprocessed dataBatch to the cluster
     * @pre no condition
     * @inv sends db to be converted if and only if it's inside the epd list to begin with
     * @post upd.size--, cluster.upd++ - sends the UPD from gpu to cluster
     */
    public void UPD(DataBatch db){
        if(upd.contains(db)){
            upd.remove(db);
            LinkedList<Object> list = cluster.getUnproccessed();
            list.add(db);
            cluster.setUnproccessed(list);
        }
    }


    /**
     * @param model Model - the model that will process the data
     * @param data LinkedList - all the data to be processed
     * @pre no condition
     * @post pd.size--, train the model / test if possible
     */
    public void PD(Model model, LinkedList<DataBatch> data){
        //still not implemented
    }
}
