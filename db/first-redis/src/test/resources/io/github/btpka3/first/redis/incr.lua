-- EVAL "${thisScript}" 1 key1 longIncrement longDefaultValue expireDurationInSeconds
local keyLen = table.getn(KEYS)
if ( keyLen ~= 1) then
    return redis.error_reply("only support exact one key, but found "
      .. keyLen .. ' : ' .. cjson.encode(KEYS))
end
local argLen = table.getn(ARGV);
if ( argLen ~= 3) then
    return redis.error_reply("only support exact three value (longIncrement,longDefaultValue,expireDurationInSeconds), but found "
      .. argLen .. ' : ' .. cjson.encode(ARGV))
end

local key = KEYS[1]
local longIncrement = tonumber(ARGV[1])
local longDefaultValue = tonumber(ARGV[2])
local expireDurationInSeconds = tonumber(ARGV[3])

redis.log(redis.LOG_WARNING, 'zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz'..cjson.encode(ARGV))
redis.log(redis.LOG_WARNING, 'key='..cmsgpack.unpack(key))
if(longIncrement~=nil)then
    redis.log(redis.LOG_WARNING, 'longIncrement='..longIncrement)
else
    redis.log(redis.LOG_WARNING, 'longIncrement=nil')
end

if(longDefaultValue~=nil)then
    redis.log(redis.LOG_WARNING, 'longDefaultValue='..longDefaultValue)
else
    redis.log(redis.LOG_WARNING, 'longDefaultValue=nil')
end

if(expireDurationInSeconds~=nil)then
    redis.log(redis.LOG_WARNING, 'expireDurationInSeconds='..expireDurationInSeconds)
else
    redis.log(redis.LOG_WARNING, 'expireDurationInSeconds=nil')
end


local typeResult = redis.call('TYPE', key)
redis.log(redis.LOG_WARNING, 'typeResult='..cjson.encode(typeResult))
if (typeResult['ok'] == "none" ) then
    if(longDefaultValue ~= nil and longDefaultValue ~= 0) then
        local setResult = redis.call('SET', key, longDefaultValue, 'KEEPTTL')
        if(setResult['ok'] ~= 'OK') then
            return redis.error_reply('failed to exec `SET '..key..' '
              ..longDefaultValue..'` KEEPTTL : ' .. cjson.encode(setResult))
        end
    end
elseif (typeResult['ok'] ~= "string" ) then
    return redis.error_reply('Key '..key..' is already with type '
      ..typeResult['ok']..'`, only `string` is supported for operation `INCRBY`')
end
if (expireDurationInSeconds ~= nil and expireDurationInSeconds > 0) then
    local expireResult = redis.call('EXPIRE', key, expireDurationInSeconds);
    if(expireResult ~= 1) then
        return redis.error_reply('failed to exec `EXPIRE '..key..' '..expireDurationInSeconds..'` : ' .. expireResult)
    end
end

local incrByResult = redis.call('INCRBY', key, longIncrement)
return incrByResult


