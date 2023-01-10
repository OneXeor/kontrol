package io.chopyourbrain.kontrol.properties

import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCObjectBase
import platform.Foundation.NSSelectorFromString
import platform.UIKit.*

@ExportObjCClass
internal class TextFieldCell : UITableViewCell {
    val title = UILabel()
    val value = UITextField()
    var textFieldProperty: TextFieldProperty? = null

    @ObjCObjectBase.OverrideInit
    constructor(style: UITableViewCellStyle, reuseIdentifier: String? = null) : super(
        style,
        reuseIdentifier
    ) {
        contentView.addSubview(title)
        contentView.addSubview(value)
        title.setTranslatesAutoresizingMaskIntoConstraints(false)
        title.leftAnchor.constraintEqualToAnchor(leftAnchor, 10.0).setActive(true)
        title.rightAnchor.constraintEqualToAnchor(rightAnchor, -10.0).setActive(true)
        title.topAnchor.constraintEqualToAnchor(topAnchor).setActive(true)
        title.bottomAnchor.constraintEqualToAnchor(value.topAnchor).setActive(true)
        value.setTranslatesAutoresizingMaskIntoConstraints(false)
        value.topAnchor.constraintEqualToAnchor(title.bottomAnchor).setActive(true)
        value.bottomAnchor.constraintEqualToAnchor(bottomAnchor).setActive(true)
        value.rightAnchor.constraintEqualToAnchor(rightAnchor, -10.0).setActive(true)
        value.leftAnchor.constraintEqualToAnchor(leftAnchor, 10.0).setActive(true)
        value.addTarget(this, NSSelectorFromString(::onTextChanged.name), UIControlEventEditingChanged)
    }


    @ObjCAction
    fun onTextChanged() {
        textFieldProperty?.onTextChangedListener?.invoke(value.text.orEmpty())
    }


    companion object Meta : UITableViewCellMeta()
}
