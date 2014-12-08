package com.wordnik.swagger.codegen.languages;

import com.wordnik.swagger.codegen.*;
import com.wordnik.swagger.models.properties.*;

import java.util.*;
import java.io.File;

public class AndroidClientCodegen extends DefaultCodegen implements CodegenConfig {
  protected String invokerPackage = "au.com.netball.android.api";
  protected String groupId = "au.com.netball";
  protected String artifactId = "netball-network-api";
  protected String artifactVersion = "1.0.0";
  protected String sourceFolder = "src/main/java";

  public String getName() {
    return "android";
  }

  public String getHelp() {
    return "Generates an Android client library.";
  }

  public AndroidClientCodegen() {
    super();
    outputFolder = "generated-code/android";
    modelTemplateFiles.put("model.mustache", ".java");
    apiTemplateFiles.put("api.mustache", ".java");
    templateDir = "android-java";
    apiPackage = "au.com.netball.android.api.client";
    modelPackage = "au.com.netball.android.api.model";

    additionalProperties.put("invokerPackage", invokerPackage);
    additionalProperties.put("groupId", groupId);
    additionalProperties.put("artifactId", artifactId);
    additionalProperties.put("artifactVersion", artifactVersion);

    supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml"));

    languageSpecificPrimitives = new HashSet<String>(
      Arrays.asList(
        "String",
        "boolean",
        "Boolean",
        "Double",
        "Integer",
        "Long",
        "Float",
        "Object")
      );
    instantiationTypes.put("array", "ArrayList");
    instantiationTypes.put("map", "HashMap");
  }

  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;
  }

  @Override
  public String apiFileFolder() {
    return outputFolder + File.separator + sourceFolder + File.separator + apiPackage().replaceAll("\\.", File.separator);
  }

  public String modelFileFolder() {
    return outputFolder + File.separator + sourceFolder + File.separator + modelPackage().replaceAll("\\.", File.separator);
  }

  @Override
  public String getTypeDeclaration(Property p) {
    if(p instanceof ArrayProperty) {
      ArrayProperty ap = (ArrayProperty) p;
      Property inner = ap.getItems();
      return getSwaggerType(p) + "<" + getTypeDeclaration(inner) + ">";
    }
    else if (p instanceof MapProperty) {
      MapProperty mp = (MapProperty) p;
      Property inner = mp.getAdditionalProperties();

      return getSwaggerType(p) + "<String, " + getTypeDeclaration(inner) + ">";
    }
    return super.getTypeDeclaration(p);
  }

  @Override
  public String getSwaggerType(Property p) {
    String swaggerType = super.getSwaggerType(p);
    String type = null;
    if(typeMapping.containsKey(swaggerType)) {
      type = typeMapping.get(swaggerType);
      if(languageSpecificPrimitives.contains(type))
        return toModelName(type);
    }
    else
      type = swaggerType;
    return toModelName(type);
  }
}