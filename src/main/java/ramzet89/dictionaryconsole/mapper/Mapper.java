package ramzet89.dictionaryconsole.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ramzet89.dictionaryconsole.pojo.DictionaryItem;
import ramzet89.dictionaryconsole.pojo.http.common.DictionaryRecord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Mapper {
    public List<DictionaryItem> toDictionaryItems(List<DictionaryRecord> dictionaryRecords) {
        return dictionaryRecords.stream()
                .map(dictionaryRecord -> {
                    var dictionaryItem = new DictionaryItem();
                    BeanUtils.copyProperties(dictionaryRecord, dictionaryItem);

                    Set<String> translations = new HashSet<>();
                    if (!dictionaryRecord.getRussian().isBlank()) translations.add(dictionaryRecord.getRussian().trim());
                    if (dictionaryRecord.getRussian2() != null) translations.add(dictionaryRecord.getRussian2().trim());
                    if (dictionaryRecord.getRussian3() != null) translations.add(dictionaryRecord.getRussian3().trim());
                    dictionaryItem.setTranslations(translations);

                    return dictionaryItem;
                })
                .collect(Collectors.toList());
    }

    public List<DictionaryRecord> toDictionaryRecords(List<DictionaryItem> dictionaryItems) {
        return dictionaryItems.stream()
                .map(dictionaryItem -> {
                    var dictionaryRecord = new DictionaryRecord();
                    BeanUtils.copyProperties(dictionaryItem, dictionaryRecord);
                    return dictionaryRecord;
                })
                .collect(Collectors.toList());
    }
}
