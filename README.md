# ModalSheet

Simple Material3 Modal Bottom Sheet that doesn't have issues with insets

## Usage

### 1. Add dependencies

![latestVersion](https://img.shields.io/github/v/release/t8rin/modalsheet?style=for-the-badge)

#### Kotlin (kts)
```kotlin
repositories {
  maven { setUrl("https://jitpack.io") } // Add jitpack
}
dependencies {
  implementation("com.t8rin.modalsheet:modalsheet:LATEST_VERSION") // Replace "LATEST_VERSION" with preferrend version tag
}
```

#### Groovy
```groovy
repositories {
  maven { url 'https://jitpack.io' } // Add jitpack
}
dependencies {
  implementation 'com.t8rin.modalsheet:modalsheet:LATEST_VERSION' // Replace "LATEST_VERSION" with preferrend version tag
}
```

### 2. Add `ModalSheet` call

```kotlin
@Composable
fun ModalSheetExample() {
   
   var showSheet by remember { mutableStateOf(false) } 
   
   ModalSheet(
       visible = showSheet,
       onVisibleChange = { showSheet = it }
   ) {
      // Your sheet content
   }
    
```

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/t8rin/ModalSheet/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/t8rin)__ me for my next creations! 🤩

# License
```xml
Designed and developed by 2023 T8RIN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
