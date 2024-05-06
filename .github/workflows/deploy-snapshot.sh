#!/bin/sh

fatal()
{
  echo "fatal: $1" 1>&2
  exit 1
}

if [ -z "${MAVEN_CENTRAL_USERNAME}" ]
then
  fatal "MAVEN_CENTRAL_USERNAME is not set."
fi

if [ -z "${MAVEN_CENTRAL_PASSWORD}" ]
then
  fatal "MAVEN_CENTRAL_PASSWORD is not set."
fi

(cat <<EOF
<settings
  xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <!-- Enable deployment of OCI images. -->
  <activeProfiles>
    <activeProfile>io7m-oci-image</activeProfile>
  </activeProfiles>

  <servers>
    <server>
      <id>sonatype-nexus-snapshots</id>
      <username>${MAVEN_CENTRAL_USERNAME}</username>
      <password>${MAVEN_CENTRAL_PASSWORD}</password>
    </server>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>${MAVEN_CENTRAL_USERNAME}</username>
      <password>${MAVEN_CENTRAL_PASSWORD}</password>
    </server>
  </servers>
</settings>
EOF
) > "$HOME/.m2/settings.xml" || fatal "could not update $HOME/.m2/settings.xml"

exec mvn \
--batch-mode \
--strict-checksums \
-DskipTests=true \
-DskipITs=true deploy
