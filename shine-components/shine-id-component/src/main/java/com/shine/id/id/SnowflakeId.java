package com.shine.id.id;

/**
 * 雪花算法生成唯一ID
 * ps: 可能需要结合分布式锁防止重复
 */
public class SnowflakeId {

    // 起始时间戳 (可以根据需要调整)
    private static final long START_TIMESTAMP = 1735660800000L; // 2021-01-01 00:00:00
    // 机器 ID 和数据中心 ID 位数
    private static final long MACHINE_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 5L;
    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;
    // 最大机器 ID 和数据中心 ID
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS); 
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS); 
    // 毫秒内序列号
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    
    private long machineId; 
    private long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeId(long machineId, long dataCenterId) {
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_ID + " or less than 0");
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("DataCenter ID can't be greater than " + MAX_DATA_CENTER_ID + " or less than 0");
        }
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long generateId() {
        long timestamp = System.currentTimeMillis();
        
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        
        lastTimestamp = timestamp;

        return ((timestamp - START_TIMESTAMP) << (MACHINE_ID_BITS + DATA_CENTER_ID_BITS + SEQUENCE_BITS)) |
               (dataCenterId << (MACHINE_ID_BITS + SEQUENCE_BITS)) |
               (machineId << SEQUENCE_BITS) |
               sequence;
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
