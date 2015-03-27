package com.wordnik.swagger.codegen.languages;

import com.wordnik.swagger.codegen.CodegenConfig;
import com.wordnik.swagger.codegen.SupportingFile;


import java.io.File;

public class AndroidRetrofitClientCodegen extends AndroidClientCodegen implements CodegenConfig {

  @Override
  public String getName() {
    return "android-retrofit";
  }

  @Override
  public String getHelp() {
    return "Generates an Android Retrofit client library.";
  }

  public AndroidRetrofitClientCodegen() {
    super();
    templateDir = "android-retrofit";

    // clear supporting files list
    supportingFiles.clear();
    supportingFiles.add(new SupportingFile("buildGradle.mustache", "", "build.gradle"));
    supportingFiles.add(new SupportingFile("apiConsts.mustache",
      (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiConsts.java"));
    supportingFiles.add(new SupportingFile("apiCallback.mustache",
      (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiCallback.java"));
    supportingFiles.add(new SupportingFile("apiError.mustache",
      (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiError.java"));
    supportingFiles.add(new SupportingFile("apiAdapter.mustache",
      (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiAdapter.java"));
  }

  /**
   * Converts the parameter names to Java standard camel-case parameter names.
   */
  @Override
  public String toParamName(String name) {
    if(name.contains("-")) {
      String[] words = name.split("-+");
      name = "";
      for(int i = 0; i < words.length; i++) {
        words[i] = words[i].toLowerCase();
        if(i != 0) {
          words[i] = words[i].replaceFirst(".", "" + Character.toUpperCase(words[i].charAt(0)));
        }
        name = name.concat(words[i]);
      }
    }
    return super.toParamName(name);
  }
}
