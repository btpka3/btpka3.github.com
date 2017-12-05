# def sort():
#     """
#     按照参数名称的字典顺序对请求中所有的请求参数进行排序
#
#     （包括文档中描述的“公共请求参数”和给定了的请求接口的自定义参数，
#     但不能包括“公共请求参数”中提到Signature参数本身）
#     :return:
#     """
#
#
# sort()


#
# def canonicalized_query_str(d):
#     """
#     1. 对于字符 A-Z、a-z、0-9以及字符“-”、“_”、“.”、“~”不编码；
#     2. 对于其他字符编码成“%XY”的格式，其中XY是字符对应ASCII码的16进制表示
#     3. 对于扩展的UTF-8字符，编码成“%XY%ZA…”的格式
#     4. 需要说明的是英文空格（ ）要被编码是%20，而不是加号（+）
#     :return:
#     """
#     print("===================== urlencode_4_sign")
#     data = {
#         "a": "aa",
#         "b": "b b",
#         "c": "中",
#         "d": "A-Za-z0-9-_.~"
#     }
#     print("---1")
#     s = parse.urlencode(data)
#     print(s)
#     print("---")
#
#
# canonicalized_query_str()
#
# query_params_str_4_url

def canonicalized_query_str(d: dict) -> str:
    from urllib import parse

    return parse.urlencode(d, encoding="utf-8") \
        .replace("%7E", "~") \
        .replace("+", "%20")


def calc_str_2_sign(httpMethod: str, d: dict) -> str:
    q = canonicalized_query_str(d)
    s = httpMethod + "&%2F&" + q
    return s


def hmac_sha1_sign(data: str, secret: str) -> str:
    import hmac
    import hashlib
    h = hmac.new(secret.encode("UTF-8"), data.encode('UTF-8'), hashlib.sha1)
    s = h.digest()
    b = s.hex()
    return b


def http_get(url):
    import urllib.request
    import json
    f = urllib.request.urlopen(url)
    resp_str = f.read;
    return json.loads(resp_str)


def nonce() -> str:
    ""


# https://github.com/QcloudApi/qcloudapi-sdk-python
class AliDns:

    def get_domain_records(self) -> list:
        []

    def add_domain_record(self, domain_name: str) -> dict:
        from urllib.parse import urlparse
        from urllib import parse

        import urllib

        data = {
            "Format": "JSON",
            "Version": "2015-01-09",
            "AccessKeyId": "TODO",
            "Signature": "TODO",
            "SignatureMethod": "HMAC-SHA1",
            "Timestamp": "TODO",
            "SignatureVersion": "1.0",
            "SignatureNonce": nonce(),

            "Action": "AddDomainRecord",
            "DomainName": domain_name,
            "RR": "TODO",
            "Type": "TXT",
            "Value": "TODO",

        }
        query_str = parse.urlencode(data)

        u = urllib.parse.urlunparse((
            "https",  # scheme
            "alidns.aliyuncs.com",  # netloc
            "/",  # path
            "",  # params
            query_str,  # query
            ""  # fragment
        ))
        {}

    def update_domain_record(self) -> dict:
        {}
