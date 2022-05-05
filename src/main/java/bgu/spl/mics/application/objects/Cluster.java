package bgu.spl.mics.application.objects;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {

	public static int count=0;		//for singleton


	private ArrayList<GPU> GPUCollection;
	private ArrayList<CPU> CPUCollection;
	private ArrayList<String> nameList;
	private int batchesCount;
	private int CPUTime;
	private int GPUTime;
	private LinkedList<Object> proccessed, unproccessed;

	public Cluster() {}

	public Cluster(ArrayList<GPU> GPUCollection, ArrayList<CPU> CPUCollection, ArrayList<String> nameList, int batchesCount, int CPUTime, int GPUTime) {
		this.GPUCollection = GPUCollection;
		this.CPUCollection = CPUCollection;
		this.nameList = nameList;
		this.batchesCount = batchesCount;
		this.CPUTime = CPUTime;
		this.GPUTime = GPUTime;
		count++;
	}

	public static int getCount() {return count;}

	public static void setCount(int count) {Cluster.count = count;}

	public ArrayList<GPU> getGPUCollection() {return GPUCollection;}

	public void setGPUCollection(ArrayList<GPU> GPUCollection) {this.GPUCollection = GPUCollection;}

	public ArrayList<CPU> getCPUCollection() {return CPUCollection;}

	public void setCPUCollection(ArrayList<CPU> CPUCollection) {this.CPUCollection = CPUCollection;}

	public ArrayList<String> getNameList() {return nameList;}

	public void setNameList(ArrayList<String> nameList) {this.nameList = nameList;}

	public int getBatchesCount() {return batchesCount;}

	public void setBatchesCount(int batchesCount) {this.batchesCount = batchesCount;}

	public int getCPUTime() {return CPUTime;}

	public void setCPUTime(int CPUTime) {this.CPUTime = CPUTime;}

	public int getGPUTime() {return GPUTime;}

	public void setGPUTime(int GPUTime) {this.GPUTime = GPUTime;}

	public void addGPUTime(int add){this.GPUTime+=add;}

	public void addCPUTime(int add){this.CPUTime+=add;}

	public void addBatchesCount(int add){this.batchesCount+=add;}

	public void addName(String add){this.nameList.add(add);}

	public void addGPUCollection(GPU add){this.GPUCollection.add(add);}

	public void addCPUCollection(CPU add){this.CPUCollection.add(add);}

	public LinkedList<Object> getProccessed() { return proccessed; }

	public void setProccessed(LinkedList<Object> proccessed) { this.proccessed = proccessed; }

	public LinkedList<Object> getUnproccessed() { return unproccessed; }

	public void setUnproccessed(LinkedList<Object> unproccessed) { this.unproccessed = unproccessed; }

	/**
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance() {
		//TODO: Implement this
		return new Cluster();
	}

}
