import java.util.*;

public class cacheStorage {
    Map<String, cacheRow> metaRowMap;// key是projectName, value是cacheRow
    public cacheStorage(List<cacheRow> list){
        metaRowMap = new HashMap<>();
        for(cacheRow row : list){
            //metaRowMap.put(row.projectName, row);
        }
    }
    public cacheStorage(Map<String, cacheRow> map){
        metaRowMap = map;
    }
    void CalculateDelta(cacheStorage newStorage){
        Set<String> oldSet = metaRowMap.keySet();
        Set<String> newSet = newStorage.metaRowMap.keySet();

        Set<String> resultAdd = new HashSet<>();
        resultAdd.addAll(oldSet);
        resultAdd.removeAll(newSet);//result包含了新增加的的project信息

        Set<String> resultDelete = new HashSet<>();
        resultDelete.addAll(oldSet);
        resultDelete.removeAll(newSet);//result包含了被删除的project信息
    }
}
