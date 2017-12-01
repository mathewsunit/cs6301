
//Driver program for LP6
package cs6301.g50;

import cs6301.g50.MDS.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;


public class rbkLP6 {
    static int VERBOSE = 0;
    static int level = 1;
    static HashMap<Integer, Container> cmap = new HashMap<>();

    static void printFive(int lineno, String operation, Long[] arr) {
        if (VERBOSE == 0) return;
        System.out.print("[" + lineno + ":" + operation + ":" + arr.length);
        if (arr != null) {
            java.util.Arrays.sort(arr);
            for (int i = 0; i < Math.min(arr.length, 50); i++) {
                System.out.print("|" + arr[i]);
            }
        }
        System.out.println("]");
    }

    static Long[] readLongArray(int len, Scanner in) {
        Container c = cmap.get(len);
        if (c == null) {
            c = new Container(len);
            cmap.put(len, c);
        }
        int index = 0;
        for (int i = 0; i < len; i++) {
            c.arr[i] = in.nextLong();
        }
        return c.arr;
    }

    static Pair[] readPairArray(int len, Scanner in) {
        Container c = cmap.get(len);
        if (c == null) {
            c = new Container(len);
            cmap.put(len, c);
        }
        int index = 0;
        for (int i = 0; i < len; i++) {
            c.parr[i].id = in.nextLong();
            c.parr[i].price = in.nextInt();
        }
        return c.parr;
    }

    static Long sumArray(Long[] arr) {
        long sum = 0;
        if (arr == null) {
            return sum;
        }
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        Scanner in;
        if (args.length > 0) {
            File file = new File(args[0]);
            in = new Scanner(file);
        } else {
            in = new Scanner(System.in);
        }
        if (args.length > 1) {
            level = Integer.parseInt(args[1]) % 10;
            VERBOSE = Integer.parseInt(args[1]) / 10;
        }
        String operation = "";
        long id, supplier, n;
        int len, minPrice, maxPrice;
        float rep;
        Long[] arr, rarr;
        Pair[] parr;
        long result = 0;
        Long returnValue = 0L;
        MDS mds = new MDS();
        cs6301.g00.Timer timer = new cs6301.g00.Timer();
        int lineno = 0;

        while (in.hasNext() && !((operation = in.next()).equals("End"))) {
            lineno++;
            returnValue = 11L;
            if (level == 2) returnValue = 7L;
            else if (level == 3) returnValue = 5L;
            else returnValue = 3L;
            switch (operation) {
                case "AI":
                    id = in.nextLong();
                    len = in.nextInt();
                    arr = readLongArray(len, in);
                    returnValue = mds.add(id, arr) ? 1L : 0L;
                    break;
                case "AR":
                    supplier = in.nextLong();
                    rep = in.nextFloat();
                    returnValue = mds.add(supplier, rep) ? 1L : 0L;
                    break;
                case "AS":
                    supplier = in.nextLong();
                    len = in.nextInt();
                    parr = readPairArray(len, in);
                    returnValue = (long) mds.add(supplier, parr);
                    break;
                case "D":
                    id = in.nextLong();
                    rarr = mds.description(id);
                    returnValue = sumArray(rarr);
                    break;
                case "FIA":
                    len = in.nextInt();
                    arr = readLongArray(len, in);
                    rarr = mds.findItem(arr);
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "FIP":
                    n = in.nextLong();
                    minPrice = in.nextInt();
                    maxPrice = in.nextInt();
                    rep = in.nextFloat();
                    rarr = mds.findItem(n, minPrice, maxPrice, rep);
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "FS":
                    id = in.nextLong();
                    rarr = mds.findSupplier(id);
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "FSR":
                    id = in.nextLong();
                    rep = in.nextFloat();
                    rarr = mds.findSupplier(id, rep);
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "I":
                    if (level < 4) break;
                    rarr = mds.identical();
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "INV":
                    len = in.nextInt();
                    arr = readLongArray(len, in);
                    rep = in.nextFloat();
                    if (level < 2) break;
                    returnValue = (long) mds.invoice(arr, rep);
                    break;
                case "P":
                    rep = in.nextFloat();
                    if (level < 3) break;
                    rarr = mds.purge(rep);
                    printFive(lineno, operation, rarr);
                    returnValue = sumArray(rarr);
                    break;
                case "R":
                    id = in.nextLong();
                    if (level < 2) break;
                    returnValue = mds.remove(id);
                    break;
                case "RD":
                    id = in.nextLong();
                    len = in.nextInt();
                    arr = readLongArray(len, in);
                    if (level < 2) break;
                    returnValue = (long) mds.remove(id, arr);
                    break;
                case "RA":
                    len = in.nextInt();
                    arr = readLongArray(len, in);
                    if (level < 3) break;
                    returnValue = (long) mds.removeAll(arr);
                    break;
                default:
                    System.out.println(lineno + ": Unknown operation: " + operation);
                    //returnValue = null;
            }
            if (returnValue != null) {
                result += returnValue;
                if (VERBOSE > 0) {
                    System.out.println(lineno + " : " + operation + " : " + returnValue);
                }
            }

        }

        System.out.println(result);
        System.out.println(timer.end());
    }

    static class Container {
        Long[] arr;
        Pair[] parr;

        Container(int n) {
            arr = new Long[n];
            parr = new Pair[n];
            for (int i = 0; i < n; i++) {
                parr[i] = new Pair(0L, 0);
            }
        }
    }
}
