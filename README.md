# EvosusSample
Simple app showing how to send a cloud message to display items on the second screen

## Setup
1. clone the repo and update the package name
2. Build the apk in Android Studio and install on the developer terminal
3. Create an app in Poynt dev portal (https://poynt.net) and download the public key pair
4. Upload the apk in the dev portal
5. Grant the **Cloud Messages** permission in the old dev portal UI (new one has a bug as of this writing [9/7/2017]) 
![](http://www.natochy.com/work/poynt/images/securitysettings.png)


## Send cloud message
Using [Poynt Python SDK](https://github.com/poynt/poynt-python): 

~~~python
[Thu Sep 07 11:17:25 dennis: ~] python
Python 2.7.12 (default, Jun 29 2016, 14:05:02) 
[GCC 4.2.1 Compatible Apple LLVM 7.3.0 (clang-703.0.31)] on darwin
Type "help", "copyright", "credits" or "license" for more information.
>>> import poynt
>>> poynt.application_id='urn:aid:e4e988fb-7d9e-42bb-bc5d-19484393494d'
>>> poynt.filename='/Users/dennis/Downloads/urn-aid-e4e988fb-7d9e-42bb-bc5d-19484393494d_publicprivatekey.pem'
>>> 
>>> doc, status_code = poynt.CloudMessage.send_cloud_message(
...     business_id='469e957c-57a7-4d54-a72a-9e8f3296adad',
...     store_id='d1f94f81-6257-41ce-83a8-54bf233fc78d',
...     device_id='urn:tid:d23eaeca-675f-3766-9c51-f6a0707e2587',
...     class_name='com.natochy.evosussample.CloudMessageReceiver',
...     package_name='com.natochy.evosussample',
...     data='{"totalAmount":500,"currency":"USD","items":[{"name":"Milk tea","quantity":1,"status":"ORDERED","tax":0,"unitOfMeasure":"EACH","unitPrice":100}]}'
... )
>>> print status_code
202
~~~

The terminal should display:
![](http://www.natochy.com/work/poynt/images/showItems.png)
