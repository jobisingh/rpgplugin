package com.vampire.rpg.options;

import java.util.HashMap;
import java.util.Map.Entry;

public class OptionsList {

    public HashMap<Option, Boolean> options = new HashMap<Option, Boolean>();

    public OptionsList(String s) {
        String[] data = s.split(" ");
        for (String a : data) {
            if (a.length() == 0)
                continue;
            try {
                String[] data2 = a.split("::");
                Option so = Option.get(data2[0]);
                boolean val = Boolean.parseBoolean(data2[1]);
                if (so != null) {
                    options.put(so, val);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Option, Boolean> e : options.entrySet()) {
            sb.append(e.getKey().toString());
            sb.append("::");
            sb.append(e.getValue());
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public boolean get(Option so) {
        if (options.containsKey(so))
            return options.get(so);
        options.put(so, so.getDefault());
        return so.getDefault();
    }

    public boolean toggle(Option so) {
        boolean s = get(so);
        s = !s;
        options.put(so, s);
        return s;
    }

}
