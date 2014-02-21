package org.oclc.ds.client.factory

import com.sun.jersey.api.client.Client
import org.oclc.http.springconfig.HttpClientConfig
import org.oclc.web.RequestFormat

import static org.hamcrest.core.Is.is as IS
import org.junit.Test
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.nullValue
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsEqual.equalTo

/*
 *  Copyright (c) 2011 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain proprietary information of OCLC Online Computer
 *  Library Center, Inc. and shall not be disclosed in whole or in any part to any third party or used by any person
 *  for any purpose, without written consent of OCLC, Inc.  Duplication of any portion of these materials shall include
 *  this notice.
 */

/**
 * BibServiceSALFactoryTest
 */
class ServiceSALFactoryTest {

    @Test
    public void do_building_sal() {
        def hostUrl = "http://url.org"
        def appName = "appName"
        def readTimeout = 1
        def defaultMaxConnections = 1

        HttpClientConfig config = new HttpClientConfig(
                defaultMaxConnectionsPerHost: 15,
                connectionManagerTimeoutMillis: 10,
                connectionTimeoutMillis: 2000,
                clientId: "discoveryservices",
                socketTimeoutMillis: 1)

        config.initialize();

        def sal = new ServiceSALFactory<MockSAL>().build_client_sal(MockSAL.class, hostUrl, appName, readTimeout, defaultMaxConnections, config.httpClient())

        assertThat "our sal is connected to the right place", sal.hostUrl, IS(equalTo(hostUrl))
        assertThat "our sal is connected to the right place", sal.appName, IS(equalTo(appName))

        assertThat "we have a client", sal.jerseyClient, IS(not(nullValue()))
    }

    static class MockSAL extends Expando {
        String hostUrl
        String appName
        Client jerseyClient
        RequestFormat requestFormat
    }
}
