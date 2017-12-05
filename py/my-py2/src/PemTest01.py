import pem

#from twisted.internet import ssl
#import ssl
#import rsa

f = "/data0/store/soft/certbot/docker/etc/letsencrypt/live/test13.kingsilk.xyz/cert.pem"
certs = pem.parse_file(f)
print certs
print certs[0]
print certs[0].__class__




# p = rsa.PublicKey._load_pkcs1_pem(f)
# print(p)
