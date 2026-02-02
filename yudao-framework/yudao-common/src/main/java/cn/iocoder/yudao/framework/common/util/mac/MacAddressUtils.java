package cn.iocoder.yudao.framework.common.util.mac;

public class MacAddressUtils {

    public static String incrementMacAddress(String macAddress) {
        if (macAddress == null || macAddress.isEmpty()) {
            throw new IllegalArgumentException("MAC address cannot be null or empty");
        }

        // 去除分隔符，验证格式
        String cleanMacAddress = macAddress.replaceAll("[:-]", "").toUpperCase();
        if (cleanMacAddress.length() != 12) {
            throw new IllegalArgumentException("Invalid MAC address format: " + macAddress);
        }

        // 将字符串转换为 long 类型
        long macValue = Long.parseLong(cleanMacAddress, 16);

        // 增加 1
        macValue++;

        // 将 long 类型转换回 12 位的十六进制字符串
        String incrementedMacAddress = String.format("%012X", macValue);

        // 重新添加分隔符
        StringBuilder formattedMacAddress = new StringBuilder();
        for (int i = 0; i < incrementedMacAddress.length(); i += 2) {
            if (i > 0) {
                formattedMacAddress.append(":");
            }
            formattedMacAddress.append(incrementedMacAddress, i, i + 2);
        }

        return formattedMacAddress.toString();
    }

}
