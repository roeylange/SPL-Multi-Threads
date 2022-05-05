package bgu.spl.mics.application.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static bgu.spl.mics.application.objects.GPU.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class GPUTest {

    private GPU gpu;

    @BeforeEach
    void setUp() {gpu=new GPU();}

    @AfterEach
    void tearDown() {}

    @Test
    void getType() {
        assertTrue(gpu.getType()==null);
        gpu.setType(RTX2080);
        assertTrue(gpu.getType()==RTX2080);
        gpu.setType(GTX1080);
        assertTrue(gpu.getType()==GTX1080);
        gpu.setType(RTX3090);
        assertTrue(gpu.getType()==RTX3090);

    }

    @Test
    void setType() {
        assertTrue(gpu.getType()==null);
        gpu.setType(RTX2080);
        assertTrue(gpu.getType()==RTX2080);
    }

    @Test
    void getCluster() {
        Cluster cluster=null;
        assertTrue(gpu.getCluster().equals(cluster));
        cluster= new Cluster();
        ArrayList ngpu=new ArrayList<GPU>();
        ngpu.add(new GPU());
        ArrayList ncpu=new ArrayList<CPU>();
        ncpu.add(new CPU());
        cluster.setGPUCollection(ngpu);
        cluster.setCPUCollection(ncpu);
        assertFalse(gpu.getCluster().equals(cluster));
        gpu.setCluster(cluster);
        assertTrue(gpu.getCluster().equals(cluster));
    }

    @Test
    void setCluster() {
        Cluster cluster=null;
        assertTrue(gpu.getCluster().equals(cluster));
        cluster= new Cluster();
        ArrayList ngpu=new ArrayList<GPU>();
        ngpu.add(new GPU());
        ArrayList ncpu=new ArrayList<CPU>();
        ncpu.add(new CPU());
        cluster.setGPUCollection(ngpu);
        cluster.setCPUCollection(ncpu);
        assertFalse(gpu.getCluster().equals(cluster));
        gpu.setCluster(cluster);
        assertTrue(gpu.getCluster().equals(cluster));
    }

    @Test
    void getModel() {
        Model model = null;
        assertTrue(gpu.getModel().equals(model));
        model = new Model();
        gpu.setModel(model);
        assertTrue(gpu.getModel().equals(model));
    }

    @Test
    void setModel() {
        Model model = null;
        assertTrue(gpu.getModel().equals(model));
        model = new Model();
        gpu.setModel(model);
        assertTrue(gpu.getModel().equals(model));
    }


    @Test
    void UPD() {
        assertTrue(gpu.getPd().size()==0);
        Data data = new Data(Data.Type.Text,0,3000);
        Student student = new Student("Moshe","madmach",Student.Degree.MSc,3,5);
        Model model = new Model("string",data,student, Model.Status.PreTrained, Model.Results.None);
        gpu.setModel(model);
        assertTrue(gpu.getUpd().size()==3);
        assertTrue(gpu.getCluster().getUnproccessed().size()==0);
        gpu.UPD(gpu.getUpd().getFirst());
        assertTrue(gpu.getUpd().size()==2);
        assertTrue(gpu.getCluster().getUnproccessed().size()==1);
        gpu.UPD(gpu.getUpd().getFirst());
        assertTrue(gpu.getUpd().size()==1);
        assertTrue(gpu.getCluster().getUnproccessed().size()==2);
        gpu.UPD(gpu.getUpd().getFirst());
        assertTrue(gpu.getUpd().size()==0);
        assertTrue(gpu.getCluster().getUnproccessed().size()==3);
    }

    @Test
    void PD() {
        Data data = new Data(Data.Type.Text,1000,1000);
        DataBatch dataBatch = new DataBatch(data,0);
        gpu.insert(dataBatch);
        Student student = new Student("Moshe","madmach", Student.Degree.MSc,1,3);
        Model model = new Model("t",data,student, Model.Status.PreTrained, Model.Results.None);
        gpu.setModel(model);

        gpu.setType(GTX1080);
        int actSize= gpu.getPd().size(), actTime=gpu.getTime();
        gpu.PD(gpu.getModel(),gpu.getPd());
        assertTrue(gpu.getPd().size()==0);
        assertTrue(gpu.getTime()==actTime+(4*actSize));


        gpu.insert(dataBatch);
        gpu.setType(RTX3090);
        actSize= gpu.getPd().size();
        actTime=gpu.getTime();
        gpu.PD(gpu.getModel(),gpu.getPd());
        assertTrue(gpu.getPd().size()==0);
        assertTrue(gpu.getTime()==actTime+actSize);


        gpu.insert(dataBatch);
        gpu.setType(RTX2080);
        actSize= gpu.getPd().size();
        actTime=gpu.getTime();
        gpu.PD(gpu.getModel(),gpu.getPd());
        assertTrue(gpu.getPd().size()==0);
        assertTrue(gpu.getTime()==actTime+(2*actSize));


    }

    @Test
    void insert() {
        gpu.setType(GTX1080);
        for(int i=0;i<18;i++){
            DataBatch db= new DataBatch();
            gpu.insert(db);
        }
        assertTrue(gpu.getPd().size()==8);

        gpu.setType(RTX2080);
        for(int i=0;i<18;i++){
            DataBatch db= new DataBatch();
            gpu.insert(db);
        }
        assertTrue(gpu.getPd().size()==16);

        gpu.setType(RTX3090);
        for(int i=0;i<18;i++){
            DataBatch db= new DataBatch();
            gpu.insert(db);
        }
        assertTrue(gpu.getPd().size()==32);

    }
}

