package au.com.outware;

import com.google.common.base.CaseFormat;
import com.wordnik.swagger.codegen.SupportingFile;
import com.wordnik.swagger.codegen.languages.ObjcClientCodegen;
import com.wordnik.swagger.models.Operation;


/**
 * Created by pece on 5/03/2015.
 */
public class BaconObjcCodegen extends ObjcClientCodegen {

  static {
    PREFIX = "CABC";
  }

  public BaconObjcCodegen() {
    super();
    templateDir = "objc-bacon";
    sourceFolder = "objc/Classes";

    supportingFiles.clear();
    supportingFiles.add(new SupportingFile("SWGObject.h", sourceFolder, "SWGObject.h"));
    supportingFiles.add(new SupportingFile("SWGObject.m", sourceFolder, "SWGObject.m"));
    supportingFiles.add(new SupportingFile("SWGApiClient.h", sourceFolder, "SWGApiClient.h"));
    supportingFiles.add(new SupportingFile("SWGApiClient.m", sourceFolder, "SWGApiClient.m"));
    supportingFiles.add(new SupportingFile("SWGFile.h", sourceFolder, "SWGFile.h"));
    supportingFiles.add(new SupportingFile("SWGFile.m", sourceFolder, "SWGFile.m"));
    supportingFiles.add(new SupportingFile("SWGDate.h", sourceFolder, "SWGDate.h"));
    supportingFiles.add(new SupportingFile("SWGDate.m", sourceFolder, "SWGDate.m"));
    supportingFiles.add(new SupportingFile("podspec.mustache", "", "cabfare-client-api.podspec"));
  }

  @Override
  public String toParamName(String name) {
    String paramName =  super.toParamName(name);

    // handle header params
    if (paramName.startsWith("X-")) {
      paramName = paramName.replaceFirst("X-", "").replace("-", "_");
      paramName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, paramName);
    }

    return paramName;
  }

  @Override
  public String toOperationId(String operationId, Operation operation) {
    if (operation.getParameters().size() > 0) {
      String firstParam = toParamName(operation.getParameters().get(0).getName());
      operationId += "With" + initialCaps(firstParam);
    }
    return operationId;
  }
}
