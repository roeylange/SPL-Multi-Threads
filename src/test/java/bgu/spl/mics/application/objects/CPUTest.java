package bgu.spl.mics.application.objects;

import static org.junit.jupiter.api.Assertions.*;

//package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.services.CPUService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CPUTest {

    private CPU cpu;

    @BeforeEach
    void setUp() { cpu = new CPU();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void getCores() {
        assertTrue(cpu.getCores()==0);
        cpu.setCores(10);
        assertTrue(cpu.getCores()==10);
    }

    @Test
    void setCores() {
        assertTrue(cpu.getCores()==0);
        cpu.setCores(10);
        assertTrue(cpu.getCores()==10);
    }

    @Test
    void getDataBatchContainer() {
        assertTrue(cpu.getDataBatchContainer().equals(new LinkedList<DataBatch>()));
        DataBatch data= new DataBatch();
        LinkedList link = new LinkedList<DataBatch>();
        link.add(data);
        cpu.setDataBatchContainer(link);
        assertTrue(cpu.getDataBatchContainer().equals(cpu.getDataBatchContainer()));
    }

    @Test
    void setDataBatchContainer() {
        assertTrue(cpu.getDataBatchContainer().equals(new LinkedList<DataBatch>()));
        DataBatch data= new DataBatch();
        LinkedList link = new LinkedList<DataBatch>();
        link.add(data);
        cpu.setDataBatchContainer(link);
        assertTrue(cpu.getDataBatchContainer().equals(cpu.getDataBatchContainer()));
    }

    @Test
    void getCluster() {
        Cluster cluster=null;
        assertTrue(cpu.getCluster().equals(cluster));
        cluster= new Cluster();
        ArrayList<CPU> listC = new ArrayList<CPU>();
        ArrayList<GPU> listG = new ArrayList<GPU>();
        listC.add(new CPU());
        listG.add(new GPU());
        cluster.setCPUCollection(listC);
        cluster.setGPUCollection(listG);
        assertFalse(cpu.getCluster().equals(cluster));
        cpu.setCluster(cluster);
        assertTrue(cpu.getCluster().equals(cluster));
    }

    @Test
    void setCluster() {
        Cluster cluster=null;
        assertTrue(cpu.getCluster().equals(cluster));
        cluster= new Cluster();
        ArrayList<CPU> listC = new ArrayList<CPU>();
        ArrayList<GPU> listG = new ArrayList<GPU>();
        listC.add(new CPU());
        listG.add(new GPU());
        cluster.setCPUCollection(listC);
        cluster.setGPUCollection(listG);
        assertFalse(cpu.getCluster().equals(cluster));
        cpu.setCluster(cluster);
        assertTrue(cpu.getCluster().equals(cluster));
    }

    @Test
    void getTime() {
        int time = 7;
        CPUService service = new CPUService("service");
        service.setCpu(cpu);
        cpu.setService(service);
        cpu.setTime(time);
        assertTrue(cpu.getTime()==time);
        assertTrue(service.getTime()==time);
    }


    @Test
    void setTime() {
        int time = 7;
        CPUService service = new CPUService("service");
        service.setCpu(cpu);
        cpu.setService(service);
        cpu.setTime(time);
        assertTrue(cpu.getTime() == time);
        assertTrue(service.getTime() == time);
    }


    @Test
    void addDataBatchContainer() {
        DataBatch tmp = new DataBatch();
        assertTrue(cpu.getDataBatchContainer().size()==0);
        cpu.addDataBatchContainer(tmp);
        assertTrue(cpu.getDataBatchContainer().size()==1);
        assertTrue(cpu.getDataBatchContainer().contains(tmp));
    }

    @Test
    void proccess() {
        Data    d_1 = new Data(Data.Type.Tabular,0,1000),
                d_2 = new Data(Data.Type.Images,0,1000),
                d_3 = new Data(Data.Type.Text,0,1000);
        LinkedList<DataBatch> list = new LinkedList<>();

        DataBatch   db_1=new DataBatch(d_1,0),
                db_2=new DataBatch(d_2,0),
                db_3=new DataBatch(d_3,0);

        list.add(db_1);
        list.add(db_2);
        list.add(db_3);
        cpu.setDataBatchContainer(list);
        int actTime=cpu.getTime();
        cpu.proccess(cpu.getDataBatchContainer().getFirst());
        assertTrue(cpu.getDataBatchContainer().size()==2);
        assertTrue(cpu.getTime()==actTime+32/(cpu.getCores()));

        actTime=cpu.getTime();
        cpu.proccess(cpu.getDataBatchContainer().getFirst());
        assertTrue(cpu.getDataBatchContainer().size()==1);
        assertTrue(cpu.getTime()==actTime+128/(cpu.getCores()));

        actTime=cpu.getTime();
        cpu.proccess(cpu.getDataBatchContainer().getFirst());
        assertTrue(cpu.getDataBatchContainer().size()==0);
        assertTrue(cpu.getTime()==actTime+64/(cpu.getCores()));

        assertTrue(cpu.getCluster().getProccessed().size()==3);
    }

}