package org.example.api.common.io;

import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.Df;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

//https://stackoverflow.com/questions/47715082/java-sigar-getting-network-traffic
//https://blog.csdn.net/aoxida/article/details/8080967
public final class MonitorUtil {

    public static String lib_path;
    private static Sigar sigar;
    private static MonitorUtil instance;
    private final Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    private final Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    private final Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    private final Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();
    private final boolean stopGettingNetworkBandwidth = false;


    public synchronized static MonitorUtil getInstance() {
        String new_library_path = null;
        if (instance == null) {
            List<String> library_paths = null;
            if (OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Windows)) {
                library_paths = Arrays.stream(System.getProperty("java.library.path").split(";")).collect(Collectors.toList());
                library_paths.add(lib_path);
                new_library_path = String.join(";", library_paths);
            } else {
                library_paths = Arrays.stream(System.getProperty("java.library.path").split(":")).collect(Collectors.toList());
                library_paths.add(lib_path);
                new_library_path = String.join(":", library_paths);
            }
            System.setProperty("java.library.path", new_library_path);

            sigar = new Sigar();
            instance = new MonitorUtil();
        }

        return instance;
    }



    public Sigar getSigar() {
        return sigar;
    }



    public CpuVO cpu_info() {
        CpuPerc cpu = null;
        try {
            cpu = sigar.getCpuPerc();
        } catch (SigarException ignored) {

        }
        return new CpuVO(
                CpuPerc.format(cpu.getUser()).replace("%", ""),
                CpuPerc.format(cpu.getSys()).replace("%", ""),
                CpuPerc.format(cpu.getWait()).replace("%", ""),
                CpuPerc.format(cpu.getNice()).replace("%", ""),
                CpuPerc.format(cpu.getIdle()).replace("%", ""),
                CpuPerc.format(cpu.getCombined()).replace("%", "")
        );
    }

    public MemoryVO memory_info() {
        Mem mem = null;
        try {
            mem = sigar.getMem();
        } catch (SigarException ignored) {
        }
        return new MemoryVO(
                (String.format("%.2f", (Long.valueOf(mem.getTotal()).floatValue() / 1024 / 1024 / 1024))),
                (String.format("%.2f", (Long.valueOf(mem.getRam()).floatValue() / 1024))),
                (String.format("%.2f", (Long.valueOf(mem.getActualUsed()).floatValue() / 1024 / 1024 / 1024))),
                (String.format("%.2f", (Long.valueOf(mem.getActualFree()).floatValue() / 1024 / 1024 / 1024))),
                (String.format("%.2f", (Double.valueOf(mem.getUsedPercent()).floatValue()))
                ));
    }


    public  JVMInfoVO jvm_info(){
        float totalMem = Runtime.getRuntime().totalMemory();
        float maxMem = Runtime.getRuntime().maxMemory();
        float freeMem = Runtime.getRuntime().freeMemory();
        String usege = new DecimalFormat("0.00").format(  (freeMem / maxMem * 100));

        return new JVMInfoVO(Sigar.formatSize(Runtime.getRuntime().totalMemory()), Sigar.formatSize(Runtime.getRuntime().maxMemory())
                , Sigar.formatSize(Runtime.getRuntime().freeMemory()), usege);


    }

    public long[] io_info() {
        FileSystemUsage usage = null;
        try {
            usage = sigar.getFileSystemUsage("/");
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return new long[]{usage.getDiskReads(), usage.getDiskWrites()};
    }

    public DiskVO disk_info(String name) {
        FileSystemUsage file = null;
        try {
            file = sigar.getFileSystemUsage(name);
        } catch (SigarException ignored) {
        }
        return new DiskVO(
                (String.format("%.2f", (Long.valueOf(file.getTotal()).floatValue() / 1000 / 1000))),
                (String.format("%.2f", (Long.valueOf(file.getFree()).floatValue() / 1000 / 1000))),
                (String.format("%.2f", (Long.valueOf(file.getAvail()).floatValue() / 1000 / 1000))),
                (String.format("%.2f", (Long.valueOf(file.getUsed()).floatValue() / 1000 / 1000))),
                (String.format("%.2f", (Double.valueOf(file.getUsePercent()).floatValue()))
                ));
    }

    public List<NetworkVo> network_info() {
        String[] net_list = new String[0];
        List<NetworkVo> list = new ArrayList<>();
        try {
            net_list = sigar.getNetInterfaceList();
        } catch (SigarException ignored) {
        }
        for (String name : net_list) {
            NetInterfaceConfig ifconfig = null;
            try {
                ifconfig = sigar.getNetInterfaceConfig(name);
            } catch (SigarException ignored) {
            }
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                continue;
            }
            NetInterfaceStat ifstat = null;

            try {
                ifstat = sigar.getNetInterfaceStat(name);
            } catch (SigarException e) {
                e.printStackTrace();
            }
            list.add(new NetworkVo(ifstat.getRxPackets(), ifstat.getTxPackets(), ifstat.getRxBytes(), ifstat.getTxBytes(), name));
        }
        return Collections.unmodifiableList(list);
    }


    public ProcessInfoVo process_Info(String id, String name) {
        try {
            return new ProcessInfoVo(
                    String.format("%.2f", sigar.getProcCpu(id).getPercent()),
                    Sigar.formatSize(sigar.getProcMem(id).getResident()),
                    sigar.getProcTime(id).getStartTime(),
                    name
            );
        } catch (SigarException ignored) {
        }
        return null;
    }


    /**
     * @param currentMap
     * @param changeMap
     * @param hwaddr     网卡默认mac 地址
     * @param current
     * @param ni         网卡名称
     */
    private void saveChange(Map<String, Long> currentMap,
                            Map<String, List<Long>> changeMap, String hwaddr, long current,
                            String ni) {
        Long oldCurrent = currentMap.get(ni);
        if (oldCurrent != null) {
            List<Long> list = changeMap.computeIfAbsent(hwaddr, k -> new LinkedList<Long>());
            list.add((current - oldCurrent));
        }
        currentMap.put(ni, current);
    }

    private long getMetricData(Map<String, List<Long>> rxChangeMap) {
        /*
         * 统计流量
         */
        long total = 0;
        for (Map.Entry<String, List<Long>> entry : rxChangeMap.entrySet()) {
            /*
             *平均值
             */
            int average = 0;
            for (Long l : entry.getValue()) {
                average += l;
            }
            total += average / entry.getValue().size();
        }
        return total;
    }

    public TrafficVo getMetricVo() {
        /*
         * 获取全部网卡信息
         */
        long count_seed = 0;
        long count_receive = 0;
        try {
            for (String ni : sigar.getNetInterfaceList()) {
                /*
                 * 网络接口统计
                 */
                NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
                /*
                 * 网络接口配置
                 */
                NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
                /*
                 * mac 默认地址
                 */
                String hw_add_r = null;
                /*
                 * 网卡不是空地址(00:00:00:00:00:00)则 赋值
                 */
                if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                    hw_add_r = ifConfig.getHwaddr();
                }
                if (hw_add_r != null) {
                    /*
                     *获取上行流量
                     */
                    long rx_Current_tmp = netStat.getRxBytes();
                    /*
                     * 保存当前流量
                     */
                    saveChange(rxCurrentMap, rxChangeMap, hw_add_r, rx_Current_tmp, ni);
                    /*
                     *获取下行流量
                     */
                    long tx_Current_tmp = netStat.getTxBytes();
                    saveChange(txCurrentMap, txChangeMap, hw_add_r, tx_Current_tmp, ni);
                    count_receive += netStat.getRxBytes();
                    count_seed += netStat.getTxBytes();
                }


            }
        } catch (SigarException ignored) {
        }
        /*
         * 获取度量数据
         */
        long total_rx = getMetricData(rxChangeMap);
        long total_tx = getMetricData(txChangeMap);
        /*
         *上行清空
         */
        for (List<Long> l : rxChangeMap.values())
            l.clear();
        /*
         *下行清空
         */
        for (List<Long> l : txChangeMap.values())
            l.clear();
        return new TrafficVo(total_tx, total_rx, Sigar.formatSize(count_seed), Sigar.formatSize(count_receive));
    }

    private Long[] getMetric() throws SigarException {
        /*
         * 获取全部网卡信息
         */
        for (String ni : sigar.getNetInterfaceList()) {
            /*
             * 网络接口统计
             */
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
            /*
             * 网络接口配置
             */
            NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
            /*
             * mac 默认地址
             */
            String hw_add_r = null;
            /*
             * 网卡不是空地址(00:00:00:00:00:00)则 赋值
             */
            if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                hw_add_r = ifConfig.getHwaddr();
            }
            if (hw_add_r != null) {
                /*
                 *获取上行流量
                 */
                long rx_Current_tmp = netStat.getRxBytes();
                /*
                 * 保存当前流量
                 */
                saveChange(rxCurrentMap, rxChangeMap, hw_add_r, rx_Current_tmp, ni);
                /*
                 *获取下行流量
                 */
                long tx_Current_tmp = netStat.getTxBytes();
                saveChange(txCurrentMap, txChangeMap, hw_add_r, tx_Current_tmp, ni);
            }
        }
        /*
         * 获取度量数据
         */
        long total_rx = getMetricData(rxChangeMap);
        long total_tx = getMetricData(txChangeMap);
        /*
         *上行清空
         */
        for (List<Long> l : rxChangeMap.values())
            l.clear();
        /*
         *下行清空
         */
        for (List<Long> l : txChangeMap.values())
            l.clear();
        return new Long[]{total_rx, total_tx};
    }

    public static class CpuVO {
        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getSys() {
            return sys;
        }

        public void setSys(String sys) {
            this.sys = sys;
        }

        public String getWait() {
            return wait;
        }

        public void setWait(String wait) {
            this.wait = wait;
        }

        public String getNice() {
            return nice;
        }

        public void setNice(String nice) {
            this.nice = nice;
        }

        public String getIdle() {
            return idle;
        }

        public void setIdle(String idle) {
            this.idle = idle;
        }

        public String getCombined() {
            return combined;
        }

        public void setCombined(String combined) {
            this.combined = combined;
        }

        public CpuVO(String user, String sys, String wait, String nice, String idle, String combined) {
            this.user = user;
            this.sys = sys;
            this.wait = wait;
            this.nice = nice;
            this.idle = idle;
            this.combined = combined;
        }

        //    用户使用率
        private String user;
        //系统使用率
        private String sys;
        //系统等待率
        private String wait;
        //    系统错误率
        private String nice;
        //空闲
        private String idle;
        //  总的使用率
        private String combined;
    }


    public static class MemoryVO {
        public String getMemory() {
            return memory;
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }

        public String getMemRam() {
            return memRam;
        }

        public void setMemRam(String memRam) {
            this.memRam = memRam;
        }

        public String getMemUsed() {
            return memUsed;
        }

        public void setMemUsed(String memUsed) {
            this.memUsed = memUsed;
        }

        public String getMemFrees() {
            return memFrees;
        }

        public void setMemFrees(String memFrees) {
            this.memFrees = memFrees;
        }

        public String getMemoryUsage() {
            return memoryUsage;
        }

        public void setMemoryUsage(String memoryUsage) {
            this.memoryUsage = memoryUsage;
        }

        //    内存总量
        private String memory;
        //内存使用量
        private String memRam;
        //使用中
        private String memUsed;
        //可用
        private String memFrees;
        //使用率
        private String memoryUsage;

        public MemoryVO(String memory, String memRam, String memUsed, String memFrees, String memoryUsage) {
            this.memory = memory;
            this.memRam = memRam;
            this.memUsed = memUsed;
            this.memFrees = memFrees;
            this.memoryUsage = memoryUsage;
        }
    }

    public static class DiskVO {
        @Override
        public String toString() {
            return "DiskVO{" +
                    "total='" + total + '\'' +
                    ", free='" + free + '\'' +
                    ", avail='" + avail + '\'' +
                    ", used='" + used + '\'' +
                    ", usePercent='" + usePercent + '\'' +
                    '}';
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getAvail() {
            return avail;
        }

        public void setAvail(String avail) {
            this.avail = avail;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getUsePercent() {
            return usePercent;
        }

        public void setUsePercent(String usePercent) {
            this.usePercent = usePercent;
        }

        public DiskVO(String total, String free, String avail, String used, String usePercent) {
            this.total = total;
            this.free = free;
            this.avail = avail;
            this.used = used;
            this.usePercent = usePercent;
        }

        private String total;
        private String free;
        private String avail;
        private String used;
        private String usePercent;
    }


    public static class NetworkVo {
        public Long getRx_packets() {
            return rx_packets;
        }

        public void setRx_packets(Long rx_packets) {
            this.rx_packets = rx_packets;
        }

        public Long getTx_packets() {
            return tx_packets;
        }

        public void setTx_packets(Long tx_packets) {
            this.tx_packets = tx_packets;
        }

        public Long getRxBytes() {
            return rxBytes;
        }

        public void setRxBytes(Long rxBytes) {
            this.rxBytes = rxBytes;
        }

        public Long getTxBytes() {
            return txBytes;
        }

        public void setTxBytes(Long txBytes) {
            this.txBytes = txBytes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private Long rx_packets;
        private Long tx_packets;
        private Long rxBytes;
        private Long txBytes;
        private String name;

        public NetworkVo(Long rx_packets, Long tx_packets, Long rxBytes, Long txBytes, String name) {
            this.rx_packets = rx_packets;
            this.tx_packets = tx_packets;
            this.rxBytes = rxBytes;
            this.txBytes = txBytes;
            this.name = name;
        }
    }


    public static class TrafficVo {
        public Long getSend() {
            return send;
        }

        public void setSend(Long send) {
            this.send = send;
        }

        public Long getReceive() {
            return receive;
        }

        public void setReceive(Long receive) {
            this.receive = receive;
        }

        public String getCount_seed() {
            return count_seed;
        }

        public void setCount_seed(String count_seed) {
            this.count_seed = count_seed;
        }

        public String getCount_receive() {
            return count_receive;
        }

        public void setCount_receive(String count_receive) {
            this.count_receive = count_receive;
        }

        public TrafficVo(Long send, Long receive, String count_seed, String count_receive) {
            this.send = send;
            this.receive = receive;
            this.count_seed = count_seed;
            this.count_receive = count_receive;
        }

        private Long send;
        private Long receive;
        private String count_seed;
        private String count_receive;
    }
    public  static  class JVMInfoVO{
        public JVMInfoVO(String total, String max, String free, String used) {
            this.total = total;
            this.max = max;
            this.free = free;
            this.used = used;
        }

        private String total;
        private String max;
        private String free;
        private String used;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }
    }
    public static class ProcessInfoVo {
        public String getCpu_used() {
            return cpu_used;
        }

        public void setCpu_used(String cpu_used) {
            this.cpu_used = cpu_used;
        }

        public String getMem_used() {
            return mem_used;
        }

        public void setMem_used(String mem_used) {
            this.mem_used = mem_used;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ProcessInfoVo(String cpu_used, String mem_used, long start_time, String name) {
            this.cpu_used = cpu_used;
            this.mem_used = mem_used;
            this.start_time = start_time;
            this.name = name;
        }

        private String cpu_used;
        private String mem_used;
        private long start_time;
        private String name;

    }

    public static void main(String[] args) throws SigarException {
        lib_path="/home/crying711/code/xianyu/617/api/web/lib/sigar";
        FileSystem fslist[] = MonitorUtil.getInstance().getSigar().getFileSystemList();
        System.out.println("??????????");

        for (FileSystem fileSystem : fslist) {
            System.out.println(fileSystem.getDirName());
            System.out.println(MonitorUtil.getInstance().disk_info(fileSystem.getDirName()));

        }
    }




    }
