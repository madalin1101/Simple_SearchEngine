package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectionContext {
    private QuerySelectionAlgorithm querySelectionAlgorithm;
    public void setAlgorithm(QuerySelectionAlgorithm algorithm) {
        this.querySelectionAlgorithm = algorithm;
    }

    public List<String> select (List<String> list, Map<String, List<Integer>> map, String s) {
        return this.querySelectionAlgorithm.select(list, map, s);
    }
}

interface QuerySelectionAlgorithm {
    List<String> select(List<String> list, Map<String, List<Integer>> map, String s);
}


class QueryAll implements QuerySelectionAlgorithm {
    @Override
    public List<String> select(List<String> list, Map<String, List<Integer>> map, String s) {
        //TODO implement with index
        List<String> output = new ArrayList<>();
        String[] strings = s.split(" ");
        for (String s1: list) {
            boolean match = true;
            for (String string : strings) {
                if (!s1.toLowerCase().contains(string.toLowerCase())) {
                    match = false;
                    break;
                }
            }
            if (match) {
                output.add(s1);
            }
        }
        return output;
    }
}

class QueryAny implements QuerySelectionAlgorithm {
    @Override
    public List<String> select(List<String> list, Map<String, List<Integer>> map, String s) {
        List<String> output = new ArrayList<>();
        String[] strings = s.split(" ");
        for (String string : strings) {
            if (map.containsKey(string.toLowerCase())) {
                List<Integer> integers = map.get(string.toLowerCase());
                for (int x : integers) {
                    if (output.indexOf(list.get(x)) == -1) {
                        output.add(list.get(x));
                    }
                }
            }
        }
        return output;
    }
}

class QueryNone implements QuerySelectionAlgorithm {
    @Override
    public List<String> select(List<String> list, Map<String, List<Integer>> map, String s) {
        List<String> output = new ArrayList<>();
        String[] strings = s.split(" ");
        for (String string : strings) {
            if (map.containsKey(string.toLowerCase())) {
                List<Integer> integers = map.get(string.toLowerCase());
                for (int x : integers) {
                    if (output.indexOf(list.get(x)) == -1) {
                        output.add(list.get(x));
                    }
                }
            }
        }
        list.removeAll(output);
        return list;
    }
}
