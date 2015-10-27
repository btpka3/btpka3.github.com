  def creteIndex() {
        if (esClient.admin().indices().prepareExists(INDEX_NAME).execute().actionGet().isExists()) {
            esClient.admin().indices().delete(new DeleteIndexRequest(INDEX_NAME)).actionGet();
        }

        esClient.admin().indices().prepareCreate(INDEX_NAME).execute().actionGet();

        def builder = new JsonBuilder();
        builder {
            properties {
                itemLastCountTime {
                    type "string"
                format "yyyy-MM-dd HH:mm:ss"
            }
            isItem {
                type "boolean"
            }
            skuId {
                type "double"
            }
            categorySysType {
                type "string"
                index "analyzed"
                analyzer "standard"
            }
            itemDescription {
                type "string"
                index "analyzed"
                analyzer "standard"
            }
            itemOnSaleTime {
                type "date"
                // TODO 日期格式
            }
            itemAuditedTime {
                type "date"
            }
            itemOrigin {
                type "string"
                index "analyzed"
                analyzer "standard"
            }
            itemSeriesNames {
                type "string"
                index "analyzed"
                analyzer "standard"
            }
            itemPropLastCountTime {
                type "date"
            }
            itemProps {
                type "object"
                _default_ {}
            }
        }
    }
    esClient.admin()
    .indices()
    .preparePutMapping(INDEX_NAME)
    .setType(TYPE_NAME)
    .setSource(builder.toString()).execute().actionGet();
}
