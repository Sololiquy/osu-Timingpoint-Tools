import java.lang.Math;
import java.io.*;

// INI UDAH DI EDIT SAMA ALFATH

public class TimingPointTools {
    double Offset;
    int intOffset;

    public TimingPointTools() {
        try {
            File input = new File("before.txt");
            File output = new File("after.txt");
            String st;
            BufferedReader read = new BufferedReader(new FileReader(input));
            BufferedReader linecheck = new BufferedReader(new FileReader(input));

            FileWriter write = new FileWriter(output, true);
            BufferedWriter write2 = new BufferedWriter(write);

            PrintWriter delete = new PrintWriter(output);
            delete.close();

            int lines = 0;
            int i = 0;
            String previousVolume = "100";
            String previousSample = "0";
            String previousType = "1";
            String previousInheret = "1";

            while (linecheck.readLine() != null)
                lines++;
            linecheck.close();
            String[] TimingPoint = new String[lines];

            // STEP 1 : Fixing decimal on offset
            while ((st = read.readLine()) != null) {
                TimingPoint[i] = st;
                String[] lineTimingPoint = TimingPoint[i].split(",");

                Offset = Double.parseDouble(lineTimingPoint[0]);
                Offset = Math.floor(Offset);
                intOffset = (int) Offset;
                lineTimingPoint[0] = Integer.toString(intOffset);

                if (lineTimingPoint[6].equals("0")) {
                    if (lineTimingPoint[5].equals("100")) {
                        lineTimingPoint[5] = previousVolume;
                    }
                    if (lineTimingPoint[4].equals("0")) {
                        lineTimingPoint[4] = previousSample;
                    }
                    if (lineTimingPoint[3].equals("1")) {
                        lineTimingPoint[3] = previousType;
                    }
                    previousVolume = lineTimingPoint[5];
                    previousSample = lineTimingPoint[4];
                    previousType = lineTimingPoint[3];
                }

                String temp = "";
                for (int j = 0; j < 8; j++) {
                    temp = temp + lineTimingPoint[j];
                    if (j != 7) {
                        temp = temp + ",";
                    }
                }
                TimingPoint[i] = temp;
                i++;
            }

            // STEP 2 : Delete redundent timingpoint
            i = 1;
            int a;
            int b;
            String[] TimingPoint2 = new String[lines];
            TimingPoint2[0] = TimingPoint[0];
            for (int j = 1; j < lines; j++) {
                String[] lineTimingPoint = TimingPoint[j - 1].split(",");
                String[] lineTimingPoint2 = TimingPoint[j].split(",");
                a = Integer.parseInt(lineTimingPoint[0]);
                b = Integer.parseInt(lineTimingPoint2[0]);
                System.out.print(lineTimingPoint[0] + " ");
                System.out.print(a + " ");
                System.out.print(lineTimingPoint2[0] + " ");
                System.out.print(b + " ");
                if (a != b && a + 1 != b &&
                        !lineTimingPoint[1].equals(lineTimingPoint2[1]) ||
                        !lineTimingPoint[2].equals(lineTimingPoint2[2]) ||
                        !lineTimingPoint[3].equals(lineTimingPoint2[3]) ||
                        !lineTimingPoint[4].equals(lineTimingPoint2[4]) ||
                        !lineTimingPoint[5].equals(lineTimingPoint2[5]) ||
                        !lineTimingPoint[6].equals(lineTimingPoint2[6]) ||
                        !lineTimingPoint[7].equals(lineTimingPoint2[7])) {
                    System.out.print(i + " added ");
                    TimingPoint2[i] = TimingPoint[j];
                    i++;
                }
                System.out.println(" ");
            }
            for (int x = 0; x < lines; x++) {
                if (TimingPoint2[x] == null) {
                    break;
                }
                write2.write(TimingPoint2[x]);
                write2.newLine();
            }
            write2.close();
            write.close();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    public static void main(String[] args) {
        TimingPointTools x = new TimingPointTools();
    }
}
