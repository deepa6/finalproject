use(function () {
    var items = [];
    //var root = currentPage.getAbsoluteParent(1);

    var listroot = properties.get("parentPage", currentPage.getPath());
     var root = pageManager.getPage(listroot);

    //var root =  currentPage.getParent(1);

    var currentNavPath = currentPage.getAbsoluteParent(2).getPath();

    var it = root.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());

    //var it =  currentNavPath.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());

    while (it.hasNext()) {
        var page = it.next();

        var selected = (page.getPath() == currentNavPath);

        items.push({
            page: page,
            selected : selected
        });
    }

    return {
        items: items
    };  
});