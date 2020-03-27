/**
 * Written by Nihaal Maipady on 03/05/20 as part of the Advanced Data Structures Project
 *
 */


import java.io.*;
import java.util.Hashtable;

/**
 * The main program of this project. It gets the name of the input file, and
 * when reading the output number n, it output the most n popular hashtags.
 *
 *Using method: java hashtagcounter sampleInput.txt
 */
public class hashtagcounter {
    public static void main(String[] args) throws IOException {
        //System.out.println(1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0));
        FibonacciHeap<String> fh = new FibonacciHeap<String>();

        Hashtable hashtags = new Hashtable();
        //build a hashtable


        BufferedReader f = new BufferedReader(new FileReader("C:/Users/nisha/OneDrive/Desktop/UF/2 Semester/Advanced Data Structures/Project/MyHashtagCounter/NewInput.txt"));
        File of = new File("sample-output_newInput.txt");
        FileOutputStream fop = new FileOutputStream(of);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");



        String line = "";
        String[] arrs = null;
        String hashtag = "";
        int fre = 0;
        int outnum = 0;


        while(true){
            line = f.readLine();
            //read a line in the input file
            if (line.equals("STOP")||line.equals("stop")){
                break;
                //if "stop" break the loop
            }
            if (line.contains("#")){
                //System.out.print("true ");
                arrs = line.split(" ");
                hashtag = arrs[0].substring(1);
                fre = Integer.parseInt(arrs[1]);
                //System.out.print(hashtag);
                //System.out.println(fre);

                if (hashtags.containsKey(hashtag)){
                    Node<String> renode = (Node<String>) hashtags.get(hashtag);

                    //System.out.print("get ");
                    //System.out.println(renode.getData());
                    fh.increaseKey(renode, fre);

                }else {
                    Node<String> node = new Node<String>(hashtag,fre);
                    hashtags.put(hashtag, node);
                    fh.insert(node, fre);
                }

            }else if(line.contains("S")){
                break;
            }else {
                outnum = Integer.parseInt(line);
                Node<String>[] outNodeList = new Node[outnum];
                //build an array of size outnum to store the output nodes
                //and reinsert them again after output

                //output n hashtags
                for (int j = 0; j < outnum; j++){
                    String outNodeData = fh.max().getData();
                    Double outNodeKey = fh.max().getKey();
                    Node<String> outNode = new Node<String>(outNodeData,outNodeKey);
                    outNodeList[j] = outNode;
                    writer.append(fh.max().getData());
                    if (j<outnum-1){
                        writer.append(",");
                    }
                    //System.out.print(fh.max().getData()+",");
                    hashtags.remove(fh.max().getData());
                    fh.removeMax();
                }
                //System.out.print("\n");

                writer.append("\n");

                //reinsert n hashtags
                for (int j = 0; j < outnum; j++){
                    hashtags.put(outNodeList[j].data, outNodeList[j]);
                    fh.insert(outNodeList[j], outNodeList[j].getKey());
                }
            }
        }

        writer.close();
        //close the output stream
        fop.close();


        f.close();
        //close the read file
        fh.clear();
        //clear the Fibonacci Heap

    }
}
