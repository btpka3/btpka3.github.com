package com.aliyun.mns.samples.Queue;

import java.util.List;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.PagingListResult;

public class ListQueueDemo {

    public static void main(String[] args){
        CloudAccount account = new CloudAccount(
            ServiceSettings.getMNSAccessKeyId(),
            ServiceSettings.getMNSAccessKeySecret(),
            ServiceSettings.getMNSAccountEndpoint());
        MNSClient client = account.getMNSClient(); //this client need only initialize once

        try
        {
            // List Queue
            String marker = null;
            do {
                PagingListResult<String> list = new PagingListResult<String>();
                try {
                    list = client.listQueueURL("cloud-", marker, 1);
                } catch (ClientException ex) {
                    ex.printStackTrace();
                } catch (ServiceException ex) {
                    ex.printStackTrace();
                }
                List<String> queues = list.getResult();
                marker = list.getMarker();

                System.out.println("Result:");
                for (String queue : queues) {
                    System.out.println(queue);
                }
            } while (marker != null && marker != "");

        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            if (se.getErrorCode().equals("QueueNotExist"))
            {
                System.out.println("Queue is not exist.Please create before use");
            } else if (se.getErrorCode().equals("TimeExpired"))
            {
                System.out.println("The request is time expired. Please check your local machine timeclock");
            }
            /*
            you can get more MNS service error code in following link.
            https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html?spm=5176.docmns/api_reference/error_code/error_response
            */
            se.printStackTrace();
        } catch (Exception e)
        {
            System.out.println("Unknown exception happened!");
            e.printStackTrace();
        }

        client.close();
    }

}
