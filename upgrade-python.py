#!/usr/bin/env python3

# Requires [Requests](https://github.com/requests/requests/).
# We can install by `pip3 install requests`.

import os
import requests

# Figure out which Gradle version to upgrade.
# See https://developer.github.com/v3/repos/releases/#get-the-latest-release.
response = requests.get("https://api.github.com/repos/gradle/gradle/releases/latest")
json = response.json()
latestGradleVersion = json["name"]
print("Latest Gradle version: " + latestGradleVersion)

# Create a new git branch to commit the changes.
newBranchName = "upgrade-to-gradle-" + latestGradleVersion
os.system("git checkout -b " + newBranchName)
os.system("git push -u origin " + newBranchName)

os.system("./gradlew wrapper --gradle-version=" + latestGradleVersion + " --distribution-type=all")

# Commit the changes & push.
os.system("git add .")
os.system("git commit -m \"Upgrade to Gradle " + latestGradleVersion + "\"")
os.system("git push")

