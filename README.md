# simpleJaxrsClient
A starter project with a single test for JaxRs client, buildable by Gradle and Eclipse

## Efficiency test results

### Server phantomjs, client on the same machine.

    J bs INFO Calling with new client...
    average: 586.0
    average: 20.0
    
    (a separate run)
    J bs INFO Calling with same client...
    request built: 321
    average: 260.0
    average: 3.0

### Server glassfish + spring, client on the same machine.

    J bs INFO Calling with new client...
    average: 850.0
    average: 40.0
    J bs INFO Calling with same client...
    request built: 447
    average: 374.0
    average: 6.2
    
    // after adding select now() in service:
    J bs INFO Calling with same client...
    request built: 484
    average: 391.0
    average: 8.55
