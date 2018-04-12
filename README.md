作业提交（单例模式，抽象工厂，原型模式，模板模式，策略模式）

branch添加

作业提交（委派模式，适配器模式，观察者模式，装饰器模式）

MyBatis课后作业
-、实用篇-20180324作业
1. Mapper在spring管理下其实是单例，为什么可以是一个
单例？ SCOPE -> application
mapper是一个接口类，mybatis底层处理是通过mapperProxy对它进行动态代理，然后通过代理类对方法进行invoke。
，因而整个过程相当于只有方法调用，不会涉及有线程问题，所以可以是一个单例的。
2. MyBatis在Spring集成下没有mapper的xml文件会不会报错，为什么？
MapperAnnotationBuilder类中定义xml的处理，因此不会报错
if (!configuration.isResourceLoaded("namespace:" + type.getName())) {
          String xmlResource = type.getName().replace('.', '/') + ".xml";
          InputStream inputStream = null;
          try {
            inputStream = Resources.getResourceAsStream(type.getClassLoader(), xmlResource);
          } catch (IOException e) {
            // ignore, resource is not required
          }
          if (inputStream != null) {
            XMLMapperBuilder xmlParser = new XMLMapperBuilder(inputStream, assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), type.getName());
            xmlParser.parse();
          }  
    }

3. TypeHandler手写

4. 手写Plugin,多个interceptor到底谁先执行？顺序由谁决定的？
通过源码分析，发现对Interceptor数组进行迭代，即interceptor执行顺序为加入到Interceptor[]的顺序。

二、实用篇-20180325作业
1.怎么验证一级缓存的存在？
同一个sqlsession中执行同一个query，通过打印日志一次，可验证一级缓存的存在
2.验证N+1问题
嵌套查询
DEBUG ({http-apr-8080-exec-1} SpringManagedTransaction.java[openConnection]:85) [2018-04-11 15:31:45,906] - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18] will not be managed by Spring
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:45,907] - ooo Using Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18]
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:45,907] - ==>  Preparing: select v.COUNTY_NAME AS countyName, p.POLL_SOURCE_NAME, p.REGULATION_TYPE, ad.DATA_POSITION, ad.POINT_DESPRATION, ad.POINT_CODE, ad.POINT_UNIT, v.UNIT_ID, ad.FACILITY_BAS_ID, round(avg(ad.AVG_VALUE),3)AVG_VALUE, round(max(ad.MAX_VALUE),3)MAX_VALUE, round(min(ad.MIN_VALUE),3)MIN_VALUE, ? AS beginDate, ? AS endDate from t_mid_hour_data ad LEFT JOIN t_base_pollsource p ON ad.POLL_SOURCE_ID = p.POLL_SOURCE_ID LEFT JOIN ( SELECT DISTINCT c.COUNTY_ID, c.COUNTY_NAME, u.UNIT_ID, uf.FACILITY_BAS_ID FROM t_base_county c LEFT JOIN t_base_unit u ON c.COUNTY_ID = u.COUNTY_ID LEFT JOIN t_base_unitfacility uf ON u.UNIT_ID = uf.UNIT_ID ) v ON IFNULL(ad.UNIT_ID,ad.FACILITY_BAS_ID) = IF(ad.UNIT_ID is not null,v.UNIT_ID,v.FACILITY_BAS_ID) WHERE ad.DATA_TIME >= ? AND ad.DATA_TIME < ? AND ad.POLL_SOURCE_ID in ('150500000001','150500000003','150500000004','150500000010','150500000011','150500000012','150500000013','150500000014','150500000016','150500000021','150500000022','150500000024','150500000032','150500000033','150500000034','150500000035','150500000036','150500000037','150500000038','150500000040','150500000042','150500000044','150500000045','150500000046','150500000047','150500000048','150500000050','150500000052','150500000053','150500000054','150500000056','150500000060','150500000063','150500000064','150500000065','150500000066','150500000070','150500000072','150500100063','150500100064','150500100067','150500100070','150500100080','150502100001','150502100002','150502100003','150502100004','150502100005','150502100006','150521100001','150523100001','150523100002','150523100003','150524100001','150524100002','150524100003','150525100001','150525100002','150526100001','150526100002','150581100001','150581100002') AND ad.POLL_SOURCE_ID = ? AND v.UNIT_ID = ? AND v.FACILITY_BAS_ID = ? GROUP BY v.COUNTY_NAME, p.POLL_SOURCE_NAME, p.REGULATION_TYPE, ad.DATA_POSITION, ad.POINT_DESPRATION, ad.POINT_CODE, ad.POINT_UNIT ORDER BY ad.POLL_SOURCE_ID ASC LIMIT ?, ? 
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:45,908] - ==> Parameters: 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String), 150500000010(String), 30(String), 69(String), 0(Long), 10(Integer)
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,164] - ooo Using Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18]
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,165] - ==>  Preparing: select distinct aty.ALARM_TYPE_NAME AS alarmTypeName from t_mid_alarm_detail ad left join t_base_alarm_type aty on ad.ALARM_TYPE = aty.ALARM_TYPE_CODE where ad.UNIT_ID = ? and ( ad.ALARM_POINT like CONCAT('%', ?, '%') or ( ad.ALARM_POINT is null and ad.alarm_type = '600' ) or ( ad.ALARM_POINT is null and ad.alarm_type = '601' and (select count(1) from t_base_alarm_rule WHERE real_point = ? and alarm_type = 602)>0 ) ) AND IFNULL(ad.FACILITY_BAS_ID,-1) = IFNULL(?,-1) AND ad.is_next = 0 AND ((ad.begin_date BETWEEN ? and ? or ad.end_date BETWEEN ? and ?) or (ad.begin_date<= ? and ad.end_date>= ?)) AND (UNIX_TIMESTAMP(ad.end_date)-UNIX_TIMESTAMP(ad.begin_date))/60> if(isnull(ad.alarm_time),0,ad.alarm_time) 
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,165] - ==> Parameters: 30(Integer), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_01(String), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_01(String), 69(Integer), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String)
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,209] - ooo Using Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18]
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,210] - ==>  Preparing: select distinct aty.ALARM_TYPE_NAME AS alarmTypeName from t_mid_alarm_detail ad left join t_base_alarm_type aty on ad.ALARM_TYPE = aty.ALARM_TYPE_CODE where ad.UNIT_ID = ? and ( ad.ALARM_POINT like CONCAT('%', ?, '%') or ( ad.ALARM_POINT is null and ad.alarm_type = '600' ) or ( ad.ALARM_POINT is null and ad.alarm_type = '601' and (select count(1) from t_base_alarm_rule WHERE real_point = ? and alarm_type = 602)>0 ) ) AND IFNULL(ad.FACILITY_BAS_ID,-1) = IFNULL(?,-1) AND ad.is_next = 0 AND ((ad.begin_date BETWEEN ? and ? or ad.end_date BETWEEN ? and ?) or (ad.begin_date<= ? and ad.end_date>= ?)) AND (UNIX_TIMESTAMP(ad.end_date)-UNIX_TIMESTAMP(ad.begin_date))/60> if(isnull(ad.alarm_time),0,ad.alarm_time) 
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,211] - ==> Parameters: 30(Integer), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_02(String), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_02(String), 69(Integer), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String)
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,258] - ooo Using Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18]
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,258] - ==>  Preparing: select distinct aty.ALARM_TYPE_NAME AS alarmTypeName from t_mid_alarm_detail ad left join t_base_alarm_type aty on ad.ALARM_TYPE = aty.ALARM_TYPE_CODE where ad.UNIT_ID = ? and ( ad.ALARM_POINT like CONCAT('%', ?, '%') or ( ad.ALARM_POINT is null and ad.alarm_type = '600' ) or ( ad.ALARM_POINT is null and ad.alarm_type = '601' and (select count(1) from t_base_alarm_rule WHERE real_point = ? and alarm_type = 602)>0 ) ) AND IFNULL(ad.FACILITY_BAS_ID,-1) = IFNULL(?,-1) AND ad.is_next = 0 AND ((ad.begin_date BETWEEN ? and ? or ad.end_date BETWEEN ? and ?) or (ad.begin_date<= ? and ad.end_date>= ?)) AND (UNIX_TIMESTAMP(ad.end_date)-UNIX_TIMESTAMP(ad.begin_date))/60> if(isnull(ad.alarm_time),0,ad.alarm_time) 
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,258] - ==> Parameters: 30(Integer), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_03(String), W3.TL_TLRD_CC06.CCXT_CCQ_ECDL_03(String), 69(Integer), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String), 2018-04-10(String), 2018-04-11(String)
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,304] - ooo Using Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@20cd7e18]
DEBUG ({http-apr-8080-exec-1} BaseJdbcLogger.java[debug]:132) [2018-04-11 15:31:48,304] - ==>  Preparing: select distinct aty.ALARM_TYPE_NAME AS alarmTypeName from t_mid_alarm_detail ad left join t_base_alarm_type aty on ad.ALARM_TYPE = aty.ALARM_TYPE_CODE where ad.UNIT_ID = ? and ( ad.ALARM_POINT like CONCAT('%', ?, '%') or ( ad.ALARM_POINT is null and ad.alarm_type = '600' ) or ( ad.ALARM_POINT is null and ad.alarm_type = '601' and (select count(1) from t_base_alarm_rule WHERE real_point = ? and alarm_type = 602)>0 ) ) AND IFNULL(ad.FACILITY_BAS_ID,-1) = IFNULL(?,-1) AND ad.is_next = 0 AND ((ad.begin_date BETWEEN ? and ? or ad.end_date BETWEEN ? and ?) or (ad.begin_date<= ? and ad.end_date>= ?)) AND (UNIX_TIMESTAMP(ad.end_date)-UNIX_TIMESTAMP(ad.begin_date))/60> if(isnull(ad.alarm_time),0,ad.alarm_time) 

三、源码分析-20180328作业
1. org.apache.ibatis.binding.MapperProxy#invoke 这个类的53行什么时候执行？
  private boolean isDefaultMethod(Method method) {
     return ((method.getModifiers()
         & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC)
         && method.getDeclaringClass().isInterface();
   }
   jdk1.8接口引入default方法，此处判断是不是接口default方法。
   
四、源码分析作业 20180331
1. TestMapper 作者为什么要设计这样的形式来做？
为什么不是一个class而是一个interface?
TestMapper是一个接口类，mybatis底层处理是通过mapperProxy对它进行动态代理，然后通过代理类对方法进行invoke。
，然后通过类名全路径和方法名寻找sql，因此一个interface就可以完成以上功能。
而且如果写成class类还需要对方法进行实现，而框架又不会使用这个方法实现

2.org.apache.ibatis.executor.BaseExecutor#queryFromDatabase 322行这行代码的意义
解决缓存击穿
3.MyBatis的plugin实现机制
plugin的实现机制是利用了java的动态代理.AOP
org.apache.ibatis.session.Configuration类加载mybatis-config.xml配置文件时，将所有plugin按配置顺序加载到
org.apache.ibatis.session.Configuration#interceptorChain集合中
在执行数据库操作时，新生成的ParameterHandler, ResultSetHandler, StatementHandler, Executor对象会被代理起来。
使用org.apache.ibatis.plugin.InterceptorChain#pluginAll方法会按interceptorChain数组顺序生成调用链
4.lazy loading 是怎么做到的？
  代理实现的，可以配置ProxyFactory的值，可选择CGLIB|JAVASSIST，3.3以后默认采用JAVASSIT。