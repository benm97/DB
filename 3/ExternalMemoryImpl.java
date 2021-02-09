import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ExternalMemoryImpl extends IExternalMemory {
    private static final int M = 5000;
    private static final int lineLenght = 52;
    private static final int blockSize = 4 * 1024;

    //    private static final int M = 8;
//    private static final int lineLenght = 52;
//    private static final int blockSize = 3 * 2 * 52;
    private static final int lineInBlock = (blockSize / (2 * lineLenght));
    private static int filesNumber;
    private static int linesNumber;

    //TODO directory
    //TODO global
    private void sortOne(String in, String tmpPath) {
        filesNumber = 0;
        linesNumber = 0;
        try {
            File tmpDir = new File(tmpPath);
            if(!tmpDir.exists() || !tmpDir.isDirectory())
            {
                if(!tmpDir.mkdir())
                {
                    throw new IOException();
                }
            }
            BufferedReader br = new BufferedReader(new FileReader(in));
            BufferedWriter bw;
            String line = null;
            do {

                bw = new BufferedWriter(new FileWriter(tmpPath + "/" + filesNumber + ".txt"));
                filesNumber++;
                ArrayList<String> mainMemory = new ArrayList<>();

                for (int i = 0; i < M * lineInBlock; i++) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    linesNumber++;
                    mainMemory.add(line);
                }
                Collections.sort(mainMemory);
                for (String newLine : mainMemory) {
                    bw.write(newLine + "\n");

                }
                //bw.write("\n");
                bw.close();
            } while (line != null);

        } catch (IOException e) {
            System.out.println("error1");
        }
    }

    private void init(String[][] mainMemory, String tmpPath, BufferedReader[] brArray) {
        try {
            for (int fileNum = 0; fileNum < filesNumber; fileNum++) { //each file
                brArray[fileNum] = new BufferedReader(new FileReader(tmpPath + "/" + fileNum + ".txt"));
                for (int lineNum = 0; lineNum < lineInBlock; lineNum++) { //each line of first block
                    mainMemory[fileNum][lineNum] = brArray[fileNum].readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("error2");
        }
    }

    private void sortTwo(String out, String tmpPath) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(out));
            String[][] mainMemory = new String[filesNumber + 1][lineInBlock];
            BufferedReader[] brArray = new BufferedReader[filesNumber];
            init(mainMemory, tmpPath, brArray);
            int[] pointer = new int[filesNumber];
            int k = 0;
            while (k < linesNumber) {

                int blockWithMin = 0;

                //find minimum
                for (int i = 0; i < filesNumber; i++) {
                    String b = mainMemory[i][pointer[i]], a =
                            mainMemory[blockWithMin][pointer[blockWithMin]];
                    if ((a == null && b != null) || (a != null && b != null && a.compareTo(b) > 0)) {
                        blockWithMin = i;
                    }
                }
                mainMemory[filesNumber][k % lineInBlock] = mainMemory[blockWithMin][pointer[blockWithMin]];
                k++;
                if (k % lineInBlock == 0 || k == linesNumber) {
                    for (int i = 0; i < lineInBlock; i++) {
                        if (mainMemory[filesNumber][i] != null) {
                            bw.write(mainMemory[filesNumber][i]);
                            mainMemory[filesNumber][i] = null;
                            bw.newLine();
                        }
                    }
                }
                if (pointer[blockWithMin] == lineInBlock - 1) {
                    //import
                    for (int i = 0; i < lineInBlock; i++) {
                        mainMemory[blockWithMin][i] = brArray[blockWithMin].readLine();
                        pointer[blockWithMin] = 0;
                    }
                } else {
                    pointer[blockWithMin]++;
                }

            }
            bw.close();
        } catch (IOException e) {
            System.out.println("error3");
        }

    }

    @Override
    public void sort(String in, String out, String tmpPath) {
        //TODO empty file
        sortOne(in, tmpPath);
        sortTwo(out, tmpPath);
    }

    @Override
    protected void join(String in1, String in2, String out, String tmpPath) {


        try {
            class Pointer {
                private BufferedReader reader;
                private int lineNumber = 0;
                private String line;
                private String id;

                private Pointer() {
                }

                private void increment() throws IOException {

                    line = reader.readLine();
                    lineNumber++;
                    if (line == null) {
                        id = null;
                    } else {
                        id = line.split(" ")[0];
                    }
                }
            }

            Pointer tr = new Pointer();
            tr.reader = new BufferedReader(new FileReader(in1));
            tr.increment();
            Pointer ts = new Pointer();
            ts.reader = new BufferedReader(new FileReader(in2));
            ts.increment();
            Pointer gs = new Pointer();
            gs.reader = new BufferedReader(new FileReader(in2));
            gs.increment();

            BufferedWriter writer = new BufferedWriter(new FileWriter(out));

            while (tr.line != null && gs.line != null) {

                while (tr.line != null && tr.id.compareTo(gs.id) < 0) {
                    tr.increment();
                }
                while (gs.line != null && tr.id.compareTo(gs.id) > 0) {
                    gs.increment();
                }

                while (tr.line != null && tr.id.compareTo(gs.id) == 0) {
//                    if(gs.lineNumber<ts.lineNumber){
//                        ts.reader = new BufferedReader(new FileReader(in2));
//                    }

                    while (ts.lineNumber < gs.lineNumber) {
                        ts.increment();
                    }

                    while (ts.line != null && ts.id.compareTo(tr.id) == 0) {
                        writer.write(tr.line);
                        writer.write(ts.line.substring(10) + "\n");
                        ts.increment();
                    }
                    tr.increment();
                }

//                if(gs.lineNumber>ts.lineNumber){
//                    gs.reader=new BufferedReader(new FileReader(in2));
                // }
                while (gs.lineNumber < ts.lineNumber) {
                    gs.increment();
                }

            }
            writer.close();
        } catch (IOException e) {
            System.out.println("error 4");
        }


    }

    @Override
    protected void select(String in, String out, String substrSelect, String tmpPath) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(in));
            BufferedWriter bw = new BufferedWriter(new FileWriter(out));
            String line;
            while ((line=br.readLine())!=null){
                if(line.substring(0,10).contains(substrSelect)){
                    bw.write(line+"\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("error");
        }

    }

    @Override
    public void joinAndSelectEfficiently(String in1, String in2, String out,
                                         String substrSelect, String tmpPath) {
        File tmpDir = new File(tmpPath);
        if(!tmpDir.exists() || !tmpDir.isDirectory())
        {
            if(!tmpDir.mkdir())
            {
                System.out.println("error");
            }
        }
        // TODO Auto-generated method stub
        select(in1,tmpPath+"/select1.txt",substrSelect,tmpPath);
        select(in2,tmpPath+"/select2.txt",substrSelect,tmpPath);
        sortAndJoin(tmpPath+"/select1.txt",tmpPath+"/select2.txt",out,tmpPath);
    }


}