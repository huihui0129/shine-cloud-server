-- 获取key

local key = KEYS[1]

local limit = tonumber(ARGV[1])

local currentLimit = tonumber(redis.call('get', key) or "0")

if currentLimit + 1 > limit
then return 0
else
    -- 增长1
    redis.call('INCRBY', key, 1)
    -- 设置过期时间 单位：毫秒
    redis.call('PEXPIRE', key, ARGV[2])
    return currentLimit + 1
end

