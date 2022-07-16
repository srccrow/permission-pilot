package eu.darken.myperm.permissions.core.types

import android.content.pm.PermissionInfo
import eu.darken.myperm.apps.core.types.BaseApp
import eu.darken.myperm.apps.core.types.requestsPermission
import eu.darken.myperm.permissions.core.Permission

class DeclaredPermission(
    val permission: PermissionInfo,
    override val label: String? = null,
    override val description: String? = null,
    override val requestingPkgs: List<BaseApp> = emptyList(),
    override val declaringPkgs: Collection<BaseApp> = emptyList(),
) : BasePermission() {

    override val grantingPkgs: Collection<BaseApp> by lazy {
        requestingPkgs
            .filter { it.requestsPermission(this) }
            .filter { it.getPermission(id)?.isGranted == true }
    }

    override val id: Permission.Id
        get() = Permission.Id(permission.name)

    override fun toString(): String = "DeclaredPermission($id)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeclaredPermission) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()
}