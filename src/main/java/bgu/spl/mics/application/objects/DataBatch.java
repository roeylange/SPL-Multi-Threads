package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private Data data;
    private int index;

    public DataBatch(){
        data=new Data();
        index=0;
    }

    public DataBatch(Data data, int index){
        this.data=data;
        this.index=index;
    }
}
