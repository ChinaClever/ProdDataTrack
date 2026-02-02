package cn.iocoder.yudao.module.pdu.util.convertor;

import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.PduTestDataDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTransitionUtil {

    public static List<PduTestDataDetail> parsePduTestDataDetail(String data) {
        List<PduTestDataDetail> details = new ArrayList<>();

        // 去掉前后方括号
        data = data.substring(1, data.length() - 1);

        // 使用正则表达式匹配每个PduTestDataDetail对象
        Pattern pattern = Pattern.compile("PduTestDataDetail\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String detailStr = matcher.group(1);
            String[] keyValuePairs = detailStr.split(", ");
            PduTestDataDetail detail = new PduTestDataDetail();

            for (String keyValuePair : keyValuePairs) {
                String[] keyValue = keyValuePair.split("=", 2);  // 确保分割成最多两个部分
                if (keyValue.length < 2) {
                    System.out.println("Warning: Invalid key-value pair: " + keyValuePair);
                    continue;
                }
                switch (keyValue[0]) {
                    case "no":
                        detail.setNo(keyValue[1]);
                        break;
                    case "testStep":
                        detail.setTestStep(keyValue[1]);
                        break;
                    case "testItem":
                        detail.setTestItem(keyValue[1]);
                        break;
                    case "testRequest":
                        detail.setTestRequest(keyValue[1]);
                        break;
                    case "testProcess":
                        detail.setTestProcess(keyValue[1]);
                        break;
                    case "testResult":
                        detail.setTestResult(Integer.parseInt(keyValue[1]));
                        break;
                    default:
                        System.out.println("Warning: Unknown key: " + keyValue[0]);
                }
            }

            details.add(detail);
        }

        return details;
    }

}
