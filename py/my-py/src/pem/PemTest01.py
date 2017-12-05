from datetime import datetime
from OpenSSL import crypto as c

f = "/data0/store/soft/certbot/docker/etc/letsencrypt/live/test13.kingsilk.xyz/cert.pem"
cert = c.load_certificate(c.FILETYPE_PEM, file(f).read())
print(datetime.strptime(cert.get_notAfter(), "%Y%m%d%H%M%SZ"))