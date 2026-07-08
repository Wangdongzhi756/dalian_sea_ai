/**
 * API 类型统一导出
 */
export * from "./common";

// 登录模块
export * from "./login";
export * from "./menu";

// System 模块
export * from "./system/user";
export * from "./system/role";
export * from "./system/menu";
export * from "./system/dept";
export * from "./system/post";
export * from "./system/dict";
export * from "./system/config";
export * from "./system/notice";

// business 模块
export * from "./business/tenant";
export * from "./business/vessel";
export * from "./business/crew";
export * from "./business/crewCertificate";
export * from "./business/vesselLicense";
export * from "./business/application";
export * from "./business/content";

// AI 模块
export * from "./ai";

// monitor 模块
export * from "./monitor/cache";
export * from "./monitor/logininfor";
export * from "./monitor/operlog";
export * from "./monitor/online";
