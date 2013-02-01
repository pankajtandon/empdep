# Employee Department Sample App
This project illustrates the design and documentation of a REST API

## Resources
This project has two resources: `Employee` and `Department`. Below is the JSON representation of each:

```java
Employee
{
    "id" : 1,
    "salary" : 103.40,
    "depId" : 2,
    "status" : "WORKING",
    "fname" : "Joe",
    "lname" : "Shmoe"
  }
  
Department
{
    "id" : 2,
    "name" : "Sales",
    "managerId" : 1
  }
```


## Links
The `Link`s that are returned by the representations of resources are contextual. Below is a description of each `rel` that is returned by the representation.

### createEmployee
This can be achieved using an HTTP POST by passing in as many elements as needed by the Employee resource.

```java
post <web-app-root>/employees --data "{fName:"Tim", lName:"Rice"}"
```

### createDepartment
This can be achieved using an HTTP POST by passing in as many elements as needed by the Department resource.

```java
post <web-app-root>/departments --data "{name:"Accounting"}"
```

### fire
This can be achieved by using an HTTP PUT 

```java
put <web-app-root>/employee/<employeeId> --data {status:"FIRED"}
```

### hire
This can be achieved by using an HTTP PUT 

```java
put <web-app-root>/employee/<employeeId> --data {status:"WORKING"}
```

### raise
This can be achieved by using an HTTP PUT 

```java
put <web-app-root>/employee/<employeeId> --data {raise:100.0}
```

