package random;

import org.apache.commons.lang3.RandomUtils;

public class Random {

    public static int nextInt(int min, int max, int threshold) {
        int val = RandomUtils.nextInt(min, max) / threshold;
        val *= threshold;
        val += (val + threshold <= max ? threshold : 0);
        return val;
    }
}
