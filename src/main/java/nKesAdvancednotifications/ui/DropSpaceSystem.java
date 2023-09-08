package nKesAdvancednotifications.ui;

import nKesAdvancednotifications.DraggableContainer;

public interface DropSpaceSystem<T>
{
	T getDragging();
	void setDragging(T t, DraggableContainer<T> from);
	DraggableContainer<T> getDraggingFrom();
	DropSpace<T> getDragHovering();
	void setDragHovering(DropSpace<T> space);
}
