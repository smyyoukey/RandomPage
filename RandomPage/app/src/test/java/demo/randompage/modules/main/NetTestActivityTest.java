package demo.randompage.modules.main;

import android.app.Activity;
import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class NetTestActivityTest {
    private Context mContext;
    private List mockedList;

    @Before
    public void setUp(){
     mContext =  Mockito.mock(Context.class);
     mockedList = Mockito.mock(List.class);
    }

    @Test
    public void getGifFolderName()  {
        Assert.assertEquals(".gif",NetTestActivity.getGifFolderName("",false));

    }


    @Test
    public void getDataFromFile() {
//        String dataFromFile = NetTestActivity.getDataFromFile(Mockito.mock(Activity.class));
//        assertNotNull(dataFromFile);
        mockedList.add("S");
        mockedList.add("M");
        mockedList.add("Y");
        Mockito.verify(mockedList,Mockito.times(1)).add("Y");
        Mockito.when(mockedList.add(1)).thenThrow(new RuntimeException()).thenReturn(true);

        File file = Mockito.mock(File.class);
        ClassUnderTest underTest = Mockito.mock(ClassUnderTest.class);
//                new ClassUnderTest();
//        Mockito.when(underTest.callInternalInstance("Y")).thenThrow(new NullPointerException()).thenReturn(true);
        Mockito.when(underTest.callInternalInstance(Mockito.anyString())).thenReturn(true);
//        System.out.println("content = "+ Mockito.verify(mockedList,Mockito.times(2)).add("Y") );
        System.out.println("content1 = "+mockedList.add(2));
        System.out.println("content2 = "+ underTest.callInternalInstance("Y"));
    }

    public class ClassUnderTest {
        public boolean callInternalInstance(String path) {
            File file = new File(path);
            return file.exists();
        }
    }
//
//    @RunWith(PowerM.class)
//    public class TestClassUnderTest {
//        @Test
//        @PrepareForTest(ClassUnderTest.class)
//        public void testCallInternalInstance() throws Exception {
//            File file = Mockito.mock(File.class);
//            ClassUnderTest underTest = new ClassUnderTest();
//            Mockito.whenNew(File.class).withArguments("bbb").thenReturn(file);
//            PowerMockito.when(file.exists()).thenReturn(true);
//            Assert.assertTrue(underTest.callInternalInstance("bbb")); } }
//


}