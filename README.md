## 原文

[组合模式实战：从 parent_id 到前端JSON，一次搞定权限菜单系统](https://blog.csdn.net/qq_45740561/article/details/118697630)

## 源码阅读指引

接口调试（菜单树）
- 完整菜单树：`GET /api/menus/tree?userId=1`
- 指定子树：`GET /api/menus/subtree/{rootId}?userId=1`（示例：`/api/menus/subtree/1000?userId=1`）
- 扁平列表：`GET /api/menus/flat?userId=1`

推荐断点位置（从外到内逐步跟）
- `MenuController`：`getMenuTree` / `getSubTree` / `getFlatMenus`
- `MenuServiceImpl`：`buildMenuTreeForUser` → `buildTree` → `sortRecursively` → `hasCycle`
- `MenuDAO`：`findAllAuthorizedFlat`（授权裁剪与“祖先补齐”）、`findAuthorizedSubTreeFlat`（BFS 子树）

组合模式示例代码（用于理解透明式 vs 安全式）
- 透明式：`xk857-main/xk857-main-core/.../patterns/composite/transparent/{Node,FileLeaf,Folder}.java`
- 安全式：`xk857-main/xk857-main-core/.../patterns/composite/safe/{Component,Composite,FileLeaf,Folder}.java`
- 建议在上述类的 `display` 等方法处下断点，配合简单对象构造即可理解两种风格差异。

