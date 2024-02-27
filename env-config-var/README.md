
# Introduction
The common variables used throughout the application are stored in yaml files at different levels.

- common-variables.yml : 
Common variables used by all components.

Stored at https://github.com/bhashini-dibd/ulca/blob/master/env-config-var/common-variables.yml
- backend-variables.yml :
Common variables used by backend components.

Stored at https://github.com/bhashini-dibd/ulca/blob/master/backend/env-config-var/backend-variables.yml
- frontend-variables.yml :
Common variables used by frontend components.

Stored at https://github.com/bhashini-dibd/ulca/blob/master/frontend/env-config-var/backend-variables.yml
- service-variables.yml :
Variables used by a service, to be created at the particular component level

## Adding New Variables
The variables are stored in yaml files as key-value pairs in this format:
KAFKA_ULCA_BOOTSTRAP_SERVER_HOST='localhost:9092'

Refer to the above section for the appropriate file to add new variables.

Every key must be unique across all files. We can assert this by :
- Check for the key name in the common files before adding.
- For the component level variables, use appropriate prefixes(module name, context) to avoid duplicate keys.