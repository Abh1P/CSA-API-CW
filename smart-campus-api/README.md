# Smart Campus API 
#### Abhi Purohit - W2072553

## Overview
This is a project containing a build of a REST-ful API, this was mainly using JAX-RS.

It Primarily manages rooms, sensors and sensor readings; Allowing users to create rooms, assign sensors to these rooms and record readngs of sensors. 

All Data is stored in memory using HashMaps.

---

## Technology Stack

- Java
- JAX-RS (Jersey)
- Maven
- Tomcat
- In-memory storage (HashMap, ArrayList)

---



## API Endpoints

### Rooms

- POST /api/v1/rooms

- GET /api/v1/rooms

- PUT /api/v1/rooms/{id}

### Sensors

- POST /api/v1/sensors

- GET /api/v1/sensors/{id}

- DELETE /api/v1/sensors/{id}

### Sensor Readings

- POST /api/v1/sensors/{id}/readings

- GET /api/v1/sensors/{id}/readings

---

## Report

### Question 1

**Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

**Answer:**

The use of JAX-RS filters is the most efficient technique that allows addressing issues related to logging, authentication, as well as request/response modifications. The point is that developers can avoid implementing the line of code Logger.info() in each resource method, but rather perform these actions through one time coding via filters. Therfore, the issue of consistency will be ensured as well as the principle of separation of concerns.

---

### Question 2

**From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

**Answer:**

By disclosing Java stack traces, confidential information such as class names, package structure, method invocations, and server configuration is revealed. Such information can be used by an attacker to determine which tools and software packages are in use and then exploit security holes that have already been identified. This kind of information can also provide the file pathnames and internal logic to help target the attack. Thus, the stack traces should not be disclosed in the response.

---

### Question 3

**Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

**Answer:**

An HTTP 422 (Unprocessable Entity) is more appropriate when the request syntax is correct, but the semantics are incorrect. In the current scenario, the JSON request syntax is correct, but it references a nonexistent resource, such as a nonexistent sensor or room. If using a 404 code, it means the endpoint requested is unavailable.

---

### Question 4

**Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path in one massive controller class?**

**Answer:**

Implementing the Sub-Resource Locator pattern will increase modularity, as each nested resource will be controlled by a separate class. The controller will not have to take care of all endpoints, but will delegate them to specific classes that manage different resources. For example, the class managing sensor readings will be responsible for the sensors' endpoints.

---

### Question 5

**You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

**Answer:**

The use of query parameters is more flexible and suitable for filtering and searching collections than using path parameters. This is because they make it possible to include several filters optionally without having to alter the URL format. For instance, it will be easy for the clients to apply various filters based on their preferences. On the other hand, inclusion of filters in the URL path will make it inflexible and difficult to expand.

---

### Question 6

**We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

**Answer:**

In case when a client uploads the data using some media type other than the one indicated at the `@Consumes(MediaType.APPLICATION_JSON)`, the client gets a 415 error code. The main reason for this is the fact that the server does not want to process data in some unsupported format.

---

### Question 7

**Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

**Answer:**

Yes, the DELETE function is idempotent. This implies that when a DELETE request is made multiple times, its effects remain unchanged after the first request. For example, in this implementation, a DELETE request deletes the resource only the first time it is issued. Further requests for a deleted resource result in "not found" responses or no effect.

---

### Question 8

**When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.**

**Answer:**

Sending back just the IDs ensures that the volume of data transmitted over the network is minimised, leading to better efficiency and lower bandwidth consumption. It is advantageous to use it in scenarios where handling big data is involved. Nonetheless, it requires additional client calls to access full information, making the client's side more complex. Sending full objects makes it easier for the client while sending more data in return.

---

### Question 9

**Why is the provision of "Hypermedia" (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

**Answer:**

The use of Hypermedia (HATEOAS) enables an API to provide links in its response messages which give pointers to the client about what options are there for further action. It becomes easy for the API to describe itself without any dependency on static documentation. The clients do not have to depend on any predefined endpoint URLs.

---

### Question 10

**In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

**Answer:**

By default, JAX-RS will generate a new instance of the resource class for each incoming request. In this way, the resource level can be ensured to be thread-safe. On the other hand, shared collections such as HashMap and ArrayList, used for in-memory storage, can still be shared across different requests. To avoid race conditons and possible potential data corruption, appropriate design techniques must be adopted to ensure safe and secure usage.
