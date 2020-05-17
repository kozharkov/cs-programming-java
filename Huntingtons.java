public class Huntingtons {

    // Returns the maximum number of consecutive repeats of CAG in the DNA string.
    public static int maxRepeats(String dna) {
        int max = 0;
        int maxSeq = 0;
        String cag = "CAG";
        if (!dna.contains(cag)) return 0;
        boolean foundCAG = false;
        for (int i = dna.indexOf(cag); i <= dna.lastIndexOf(cag); i = i + 3) {
            // StdOut.println("position: " + i + "; piece = " + dna.substring(i, i+3));
            if (dna.substring(i, i+3).equals(cag)) {
                maxSeq++;
                foundCAG = true;
                // StdOut.println("+1; position: " + i + "; counted: " + maxSeq);
            }
            else if (foundCAG) {
                if (max < maxSeq) max = maxSeq;
                maxSeq = 0;
                foundCAG = false;
                i = dna.indexOf(cag, i+1) - 3;
                // StdOut.println("reset m... max = " + maxSeq + "; max = " + max);
                // StdOut.println("next search will start at: " + (i + 3));
            }
        }
        if (max < maxSeq) max = maxSeq;
        return max;
    }

    // Returns a copy of s, with all whitespace (spaces, tabs, and newlines) removed.
    public static String removeWhitespace(String s) {
        return s.replace(" ", "").replace("\n", "").replace("\r", "").replace("\t", "");
    }

    // Returns one of these diagnoses corresponding to the maximum number of repeats:
    // "not human", "normal", "high risk", or "Huntington's".
    public static String diagnose(int maxRepeats) {
        if (maxRepeats < 10 || maxRepeats >= 181) return "not human";
        else if (maxRepeats < 36) return "normal";
        else if (maxRepeats < 40) return "high risk";
        else return "Huntington's";
    }

    // Sample client (see below).
    public static void main(String[] args) {
        In f = new In(args[0]);
        String dna = f.readAll();
        // StdOut.println(dna);
        dna = removeWhitespace(dna);
        // StdOut.println("removed whitespaces:");
        // StdOut.println(dna);
        // StdOut.println("------");
        int m = maxRepeats(dna);
        StdOut.println("max repeats = " + m);
        StdOut.println(diagnose(m));
    }

}
