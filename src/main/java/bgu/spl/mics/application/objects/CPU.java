package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.services.CPUService;

import java.util.LinkedList;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {
    private int cores;
    private LinkedList<DataBatch> dataBatchContainer;
    private Cluster cluster;
    private CPUService service;

    public CPU() {
        cores=0;
        dataBatchContainer=new LinkedList<DataBatch>();
        cluster=null;
        service=new CPUService("Service");
    }

    public CPU(int cores, Cluster cluster, CPUService service) {
        this.cores = cores;
        this.dataBatchContainer=new LinkedList<DataBatch>();
        this.cluster=cluster;
        this.service=service;
    }


    /**
     * @return int number of cores
     */
    public int getCores() {return cores;}

    /**
     * @param cores int - to set as the new num of cores 
     * @pre none
     * @post this.cores==cores
     */
    public void setCores(int cores) {this.cores = cores;}
    /**
     * @return return dataBatchContainer
     */
    public LinkedList<DataBatch> getDataBatchContainer() {return dataBatchContainer;}

    /**
     * @param dataBatchContainer LinkedList<DataBatch> - to set as the new list
     * @pre none
     * @post this.dataBatchContainer.equals(dataBatchContainer)
     */
    public void setDataBatchContainer(LinkedList<DataBatch> dataBatchContainer) {this.dataBatchContainer = dataBatchContainer;}

    /**
     * @return return cluster
     */
    public Cluster getCluster() {return cluster;}


    /**
     * @param cluster Cluster - to set as the new cluster
     * @pre none
     * @post this.cluster==cluster
     */
    public void setCluster(Cluster cluster) {this.cluster = cluster;}

    /**
     * @return return service
     */
    public CPUService getService() { return service; }


    /**
     * @param service CPUService - to set as the new list
     * @pre none
     * @post this.service==service
     */
    public void setService(CPUService service) { this.service = service; }

    /**
     * @pre no condition
     * @post dataBatchContainer.size()++
     */
    public void addDataBatchContainer(DataBatch add){dataBatchContainer.add(add);}

    /**
     * @return return time
     */
    public int getTime() {return service.getTime(); }

    /**
     * @param time int - new time to set
     * @pre no condition
     * @post this.time==time
     */
    public void setTime(int time){service.setTime(time);}


    /**
     * @pre no condition
     * @post unprocessed DataBatch "data" -> processed DataBatch "data"
     */
    public void proccess(DataBatch data){}
}
