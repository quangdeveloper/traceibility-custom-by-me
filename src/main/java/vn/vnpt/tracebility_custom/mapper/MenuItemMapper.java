package vn.vnpt.tracebility_custom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vn.vnpt.tracebility_custom.entity.MenuItem;
import vn.vnpt.tracebility_custom.model.request.MenuItemNewRQ;
import vn.vnpt.tracebility_custom.model.request.MenuItemUpdateRQ;
import vn.vnpt.tracebility_custom.model.response.MenuItemRP;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mappings({
            @Mapping(source = "name", target = "key"),
            @Mapping(source = "name", target = "label"),
    })
    MenuItemRP toMenuItemRpFromMenuItem(MenuItem menuItem);

    @Mappings({
    })
    MenuItem toMenuItemFromMEnuItemNewRQ(MenuItemNewRQ menuItemNewRQ);

    @Mappings({
    })
    MenuItem toMenuItemFromMEnuItemUpdateRQ(MenuItemUpdateRQ menuItemUpDateRQ);

}
