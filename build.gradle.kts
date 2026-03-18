import utilities.isSnapshot
import utilities.writeVersion

plugins {
    id("turtlshell.root-conventions")
}

// Start with the base: 1.0.1+1.21.1
val base = project.writeVersion(utilities.VersionType.FULL)

// Append the Git info ONLY if it's a snapshot
version = if (project.isSnapshot()) {
    val branch = versioning.info.branch.substringAfter("/")
    "$base-$branch-${versioning.info.build}"
} else {
    base
}