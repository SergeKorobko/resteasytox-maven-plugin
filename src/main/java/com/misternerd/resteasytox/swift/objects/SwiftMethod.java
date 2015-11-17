package com.misternerd.resteasytox.swift.objects;

import java.util.ArrayList;
import java.util.List;

import com.misternerd.resteasytox.swift.helper.BuildableHelper;

public class SwiftMethod extends Buildable implements ParameterBuildable
{
	protected static final String INIT_FUNCTION_NAME = "init";

	private final String name;

	private final List<ParameterBuildable> parameters = new ArrayList<>();

	private final List<String> body = new ArrayList<>();

	private boolean isStatic = false;
	
	private final String returnType;


	public SwiftMethod(String name)
	{
		super();
		this.name = name;
		this.returnType = "Void";
	}


	public void addParameter(ParameterBuildable parameter)
	{
		parameters.add(parameter);
	}


	public void addBody(String line)
	{
		body.add(line);
	}


	public void addBody(String line, Object... args)
	{
		body.add(String.format(line, args));
	}


	public void setStatic(boolean isStatic)
	{
		this.isStatic = isStatic;
	}


	public void build(StringBuilder sb, int indent)
	{
		BuildableHelper.addIndent(sb, indent);

		if (INIT_FUNCTION_NAME.equals(name))
		{
			sb.append(INIT_FUNCTION_NAME);
		}
		else
		{
			if (isStatic) {
				sb.append("static ");
			}
			sb.append("func ").append(name);
		}
		sb.append("(");
		addParameters(sb);
		sb.append(")");
		if (!INIT_FUNCTION_NAME.equals(name))
		{
			sb.append(" -> ").append(returnType).append(" {");
		}

		indent++;
		for (String line : body)
		{
			BuildableHelper.addNewline(sb);
			BuildableHelper.addIndent(sb, indent);
			sb.append(line);
		}
		indent--;

		BuildableHelper.addNewline(sb);
		BuildableHelper.addIndent(sb, indent);
		sb.append("}");
	}


	private void addParameters(StringBuilder sb)
	{
		for (int i = 0; i < parameters.size(); i++)
		{
			ParameterBuildable parameter = parameters.get(i);
			parameter.buildParameter(sb);

			if (i < parameters.size() - 1)
			{
				sb.append(", ");
			}
		}
	}


	@Override
	public void buildNewline(StringBuilder sb, int indent)
	{
		BuildableHelper.addSpace(sb);

		build(sb, indent);
	}


	@Override
	public void build(StringBuilder sb)
	{
		build(sb, 0);
	}


	@Override
	public void buildParameter(StringBuilder sb)
	{
		sb.append(name).append(": (");
		addParameters(sb);
		sb.append(") -> ").append(returnType);
	}

}
