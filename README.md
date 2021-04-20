# modular java samples

## prerequisite

* Apache Karaf 설치해야함. [link](https://www.apache.org/dyn/closer.lua/karaf/4.1.3/apache-karaf-4.1.3.zip)
  * Apache Karaf는 *Apache Felix의 OSGi 스펙을 base로 구현된 OSGi application이 구동되는 platform* 임
  * {KARAF_HOME}/bin/karaf start 로 구동

## simple OSGi test

* com.ijys:modular-activator:1.0-SNAPSHOT
  <pre>mvn clean install로 maven local repository jar install
  {KARAF_HOME}/bin/kakaf start 로 Karaf 시작

  karaf@root()> bundle:install mvn:com.ijys/modular-activator/1.0-SNAPSHOT
  Bundle ID: 53
  karaf@root()> bundle:start 53
  Hello World
  karaf@root()> bundle:stop 53
  Goodbye World
  karaf@root()> bundle:start 53
  Hello World
  karaf@root()> bundle:stop 53
  Goodbye World
  karaf@root()> bundle:uninstall 53
  karaf@root()> bundle:start 53
  Bundle 53 is invalid
  </pre>

## OSGi service

* com.ijys:modular-service:1.0-SNAPSHOT
  <pre>mvn clean install</pre>

## OSGi client

* com.ijys:modular-client:1.0-SNAPSHOT
  <pre>mvn clean install</pre>

## service & client

<pre>karaf@root()> bundle:install mvn:com.ijys/modular-service/1.0-SNAPSHOT
Bundle ID: 58
karaf@root()> bundle:install mvn:com.ijys/modular-client/1.0-SNAPSHOT
Bundle ID: 60
karaf@root()> start 60
karaf@root()> start 58
Registering service.
Notification of service registered.
Hello John</pre>

* client 시작시, client에서 service에 대한 serviceListner 설정되었음.
  * Client.java의 start
  <pre>
  ctx.addServiceListener(
      this, "(objectclass=" + Greeter.class.getName() + ")");
  </pre>
* service 변경시, client의 serviceChanged method가 callback 됨 with ServiceEvent.
  <pre>
  public void serviceChanged(ServiceEvent serviceEvent) {
    int type = serviceEvent.getType();
    switch (type){
        case(ServiceEvent.REGISTERED):
            System.out.println("Notification of service registered.");
            serviceReference = serviceEvent
              .getServiceReference();
            Greeter service = (Greeter)(ctx.getService(serviceReference));
            System.out.println( service.sayHiTo("John") );
            break;
        case(ServiceEvent.UNREGISTERING):
            System.out.println("Notification of service unregistered.");
            ctx.ungetService(serviceEvent.getServiceReference());
            break;
        default:
            break;
    }

}
  </pre>

#### ref. https://www.baeldung.com/osgi
