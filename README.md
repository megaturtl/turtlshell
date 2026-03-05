# turtlshell

The base for megaturl's minecraft mods.

---

## CI

### How versioning works

The base version is set once per release cycle in `gradle.properties`:

```properties
mod_version=1.1.0-alpha
```

Github Actions appends a build identifier to the base version depending on the context. Local builds use the base version as-is.

| Context | Example jar name                                | How the version is set |
|---|-------------------------------------------------|---|
| Local build | `turtlshell-fabric-1.1.0-alpha.jar`              | Direct from `gradle.properties` |
| Merged to `main` | `turtlshell-fabric-1.1.0-alpha+a3f92c1.jar`      | Base version + short commit SHA |
| Pull request | `turtlshell-fabric-1.1.0-alpha+pr42.b8d1f03.jar` | Base version + PR number + short SHA |
| Tagged release | `turtlshell-fabric-1.1.0-alpha.jar`             | Taken directly from the tag name, `gradle.properties` ignored |

---

## The 3 Build Workflows

### `build-pull-request` - runs on every PR

Verifies that a PR compiles and produces valid jars for both platforms.
Artifacts are uploaded to the Actions run so you can download and test
them without checking out the branch locally.

This workflow is **read-only** with respect to the Gradle cache. PR code
is untrusted and cannot write to the cache that `main` branch builds
depend on.

### `build-commit` - runs on every push to `main`

Verifies that `main` is healthy after each merge, and **keeps the Gradle
cache warm**. This is the only workflow that writes to the cache, PRs read from this and can still build fast.

Artifacts are named by full commit SHA so any build from `main` is
permanently traceable.

### `build-tag` - runs when a `v*` tag is pushed

Builds the official release artifact. The version is taken from the tag
name, not `gradle.properties`, so the jar is always consistent with the tag.

This workflow will runs under the `prod` GitHub Environment to:
- Restrict deployments to tags matching `v*`
- (In the future) Require manual approval before the job runs. Will be useful if publishing gets set up.
- (In the future) Hold any private env variables separately from dev builds.

---

## Example: Creating a release build

1. Make sure `main` is in a state ready for release.
2. Push a tag:
   ```bash
   git tag v1.1.0-alpha
   git push origin v1.1.0-alpha
   ```
3. The `build-tag` workflow triggers.
4. Download the artifacts from the completed Actions run.

Tag naming convention: the tag name minus the `v` prefix becomes the
mod version. `v1.1.0` -> `1.1.0`. `v1.2.0-beta` -> `1.2.0-beta`.
