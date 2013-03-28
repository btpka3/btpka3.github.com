using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Web.Services.Protocols;
using System.Text;
using Microsoft.Web.Services3;
using Microsoft.Web.Services3.Design;
using Microsoft.Web.Services3.Security;
using Microsoft.Web.Services3.Security.Tokens;
using ConsoleApplication2.localhost;
using Microsoft.Web.Services3.Addressing;


namespace ConsoleApplication2
{
    class Program
    {
        static void Main(string[] args)
        {
            MyMathServiceWse serviceProxy = new MyMathServiceWse();

            //UsernameToken token = new UsernameToken("zhang3", "f0f5671b9ec568d03cbe126ce135a8b3d6ce6ab2", PasswordOption.SendPlainText);
            //serviceProxy.SetClientCredential(token);

            //X509Certificate2 myServerCert = new X509Certificate2("myServer.pem.cer");
            //serviceProxy.SetServiceCredential(new X509SecurityToken(myServerCert));

            

            
            UsernameForCertificateAssertion assert = new UsernameForCertificateAssertion();
            assert.RenewExpiredSecurityContext = true;
            assert.RequireSignatureConfirmation = false;
            assert.Protection.DefaultOperation.Request.CustomSignedHeaders.Clear();
            assert.Protection.DefaultOperation.Request.EncryptBody = true;
            assert.Protection.DefaultOperation.Request.SignatureOptions = SignatureOptions.IncludeNone;
            assert.Protection.DefaultOperation.Response.EncryptBody = false;
            assert.Protection.DefaultOperation.Response.SignatureOptions = SignatureOptions.IncludeNone;

            MutualCertificate10Assertion assertion1 = new MutualCertificate10Assertion()
            {
                EstablishSecurityContext = false,
                RenewExpiredSecurityContext = true,
                RequireSignatureConfirmation = false,
                MessageProtectionOrder = MessageProtectionOrder.SignBeforeEncrypt,
                RequireDerivedKeys = false,
                TtlInSeconds = 300
            };
            //assertion1.ClientX509TokenProvider = new X509TokenProvider(StoreLocation.CurrentUser, StoreName.My, "CN=myClient,OU=R & D department,O=BJ SOS Software Tech Co.\\, Ltd,L=Beijing,ST=Beijing,C=CN", X509FindType.FindBySubjectDistinguishedName);
            assertion1.ClientX509TokenProvider = new X509TokenProvider(StoreLocation.CurrentUser, StoreName.My, "1364458997", X509FindType.FindBySerialNumber);
            assertion1.ServiceX509TokenProvider = new X509TokenProvider(StoreLocation.CurrentUser, StoreName.AddressBook, "1364458964", X509FindType.FindBySerialNumber);
            Console.Out.WriteLine(111);
            Console.Out.WriteLine(assertion1.ClientX509TokenProvider.GetToken()); 
            Console.Out.WriteLine(111);
            Console.Out.WriteLine(assertion1.ServiceX509TokenProvider.GetToken());
            Console.Out.WriteLine(222);
            //protection
            assertion1.Protection.Request.SignatureOptions = SignatureOptions.IncludeSoapBody;
            assertion1.Protection.Request.EncryptBody = true;
            assertion1.Protection.Response.SignatureOptions = SignatureOptions.IncludeSoapBody;
            assertion1.Protection.Response.EncryptBody = true;
            assertion1.Protection.Fault.SignatureOptions = SignatureOptions.IncludeSoapBody;
            assertion1.Protection.Fault.EncryptBody = false;

            Policy policy = new Policy();
            policy.Assertions.Add(assertion1);
            serviceProxy.SetPolicy(policy);

            //serviceProxy.RequestSoapContext.Addressing.Action = new Action("http://www.test.me/MyMath/add");

            addRequest req = new addRequest();
            req.x = 1;
            req.y = 2;
            addResponse resp = serviceProxy.add(req);
            Console.Out.WriteLine("resp = " + resp.@out);
             
        }
    }
}
