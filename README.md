1���������Ҫ����aidl�ļ�����onBind�����з���messenger��BInder���������Ҫ����һ��messenger�����ˣ�������Ҫ����һ��handler����
2�����handler������������ͻ��˷�������Ϣ��ʹ��Messenger messengerFromClient = msg.replyTo;��ȡ�ͻ��˵�messenger������ʹ������send Message
3����������service�еģ�ͬ����Ҫע��
4���ͻ���ͬ����ʹ��bindService
5����onServiceConnected�л�ȡ����˵�messenger�������������aidl�Ĳ�һ����
6����message��replyTo��������Ϊ�ͻ��˵�messenger�����������Ҫ����Ȼ������ò����ͻ��˵Ķ���