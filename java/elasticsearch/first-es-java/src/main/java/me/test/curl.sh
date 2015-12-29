SERVER="test13.kingsilk.xyz:9200"
INDEX="lizi"
TYPE="item"

# 删除索引
curl -XDELETE "http://${SERVER}/${INDEX}?pretty"

# 创建索引
curl -XPUT "http://${SERVER}/${INDEX}?pretty"

# 明确指定maping （注意：动态字段通过模板设置. 已有数据的情况下，mapping不能被更新）
# https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-root-object-type.html#_dynamic_templates
curl -XPUT "http://${SERVER}/${INDEX}/_mapping/${TYPE}?pretty" -d '{
    "item" : {
        "dynamic_templates" : [{
            "props_tpl" : {
                "path_match" : "props.*",
                "mapping" : {
                    "type" : "{dynamic_type}",
                    "index" : "not_analyzed"
                }
            }
        }],

        "properties":{
            "_all" : {
                "type":"string",
                "index": "analyzed",
                "analyzer": "standard"
            },
            "title" : {
                "type":"string",
                "index": "analyzed",
                "analyzer": "standard"
            },
            "origin" : {
                "type":"string",
                "index": "analyzed",
                "analyzer": "standard"
            },
            "description" : {
                "type":"string",
                "index": "analyzed",
                "analyzer": "standard"
            },
            "sales_count" : {
                "type":"long"
            },
            "price" : {
                "type":"long"
            }
        }
    }
}'



# 批量创建文档
curl -XPOST "http://${SERVER}/${INDEX}/${TYPE}/_bulk?pretty" -d '
{"index":{"_id":"11"}}
{"title": "MISSHA谜尚维他保湿裸妆金色BB霜50ml", "origin" : "韩国", "description": "这不是面膜，快来买啊，亲" ,"sales_count":748, "price" : 9680, "props":{"季节":"春季","尺寸":"150*200cm"}}
{"index":{"_id":"12"}}
{"title": "MISSHA谜尚双头眼影棒+眼线笔",        "origin" : "韩国", "description": "亲耐的，再不买就卖光光喽" ,"sales_count":666, "price" : 1080, "props":{"季节":"夏季","尺寸":"170*220cm"}}
{"index":{"_id":"21"}}
{"title": "谜尚Missha指甲油白色盖子",           "origin" : "日本", "description": "亲耐的，再不买就卖光光喽" ,"sales_count":666, "price" : 990, "props":{"季节":"秋季","尺寸":"180*200cm"}}
'

# 单个创建文档(全部替换)
curl -XPUT "http://${SERVER}/${INDEX}/${TYPE}/22?pretty" -d '
{"title": "SKINFOOD思亲肤番茄西红柿面霜40g",    "origin" : "美国", "description": "跳楼价，最后一天啦"      ,"sales_count":777, "price" : 8800, "props":{"季节":"冬季","尺寸":"200*220cm"},propsAAA:"中国"}
'

# 更新单个文档(部分更新)
curl -XPOST "http://${SERVER}/${INDEX}/${TYPE}/22/_update?pretty" -d '
{
    "doc": {
        "description": "跳楼价，最后一天啦~~~"
    }
}'

# 确认创建文档后，动态字段的 mapping 
curl -XGET "http://${SERVER}/${INDEX}/_mapping/${TYPE}?pretty"




# 查询所有
curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "match_all" : {}
    }
}'

# 关键词查询
curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "match" : { "_all" : "双白" }
    }
}'

# 数值型范围过滤
curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "filtered": {
            "query": {
              "match_all": {}
            },
            "filter": {
                "range": { "price": { "gte": 1200 }}
            }
        }
    } 
}'


# 排序

curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "match_all" : { }
    },
    "sort" : [
        { "sales_count" : {"order" : "desc"}},
        "price"
    ]
}'

# 高亮

curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "match" : { "_all" : "双白日" }
    },
    "highlight" : {
        "fields" : {
            "title" : {},
            "origin" :{}
        }
    }
}'

# 字段匹配
curl -XGET "http://${SERVER}/${INDEX}/${TYPE}/_search?pretty" -d '
{
    "query" : {
        "term" : { "季节" : "春季" }
    },
    "highlight" : {
        "fields" : {
            "*" :{}
        }
    }
}'
 