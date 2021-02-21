package Filters;

/**
 * A singleton that is used for parsing the filter commands.
 */
public class FilterFactory {
	private static FilterFactory filterFactory = new FilterFactory();
	/*possible correct filter type name strings */
	private static final String GREATER_THAN = "greater_than",
								BETWEEN = "between",
								SMALLER_THAN = "smaller_than",
								FILE = "file",
								CONTAINS = "contains",
								PREFIX = "prefix",
								SUFFIX = "suffix",
								WRITABLE = "writable",
								EXECUTABLE = "executable",
								HIDDEN = "hidden",
								ALL = "all";
	/*amount of arguments excluding "NOT" */
	private static int argAmount;
	/*the actual filter name */
	private static String typename;
	private static String firstArgument = null;
	private static String secondArgument = null;
	private static Filter filter = null;
	/*true if the last argument was "NOT" */
	private static boolean negative = false;

	private FilterFactory() {
	}

	public static boolean parseFilter(String command) {
		String[] args = command.split("#");
		if (!labelArguments(args))
			return false;
		if (!typeTest(typename))
			return false;
		if (!legalArguments())
			return false;
		if (negative)
			filter = new Negative(filter);
		return true;
	}


	private static boolean labelArguments(String[] args) {
		argAmount = args.length;
		if (args[argAmount - 1].equals("NOT")) {
			argAmount--;
			negative = true;
		} else {
			negative = false;
		}
		if (argAmount == 0 || argAmount > 3) {
			return false;
		}
		if (argAmount > 0) {
			typename = args[0];
		}
		if (argAmount > 1) {
			firstArgument = args[1];
		} else {
			firstArgument = null;
			secondArgument = null;
		}
		if (argAmount > 2) {
			secondArgument = args[2];
		} else {
			secondArgument = null;
		}
		return true;
	}

	private static boolean typeTest(String typeName) {
		switch (typeName) {
			case GREATER_THAN:
				filter = new GreaterThan();
				break;
			case BETWEEN:
				filter = new Between();
				break;
			case SMALLER_THAN:
				filter = new SmallerThan();
				break;
			case FILE:
				filter = new FileName();
				break;
			case CONTAINS:
				filter = new Contains();
				break;
			case PREFIX:
				filter = new Prefix();
				break;
			case SUFFIX:
				filter = new Suffix();
				break;
			case WRITABLE:
				filter = new Writable();
				break;
			case EXECUTABLE:
				filter = new Executable();
				break;
			case HIDDEN:
				filter = new Hidden();
				break;
			case ALL:
				filter = new All();
				break;
			default:
				filter = new All();
				return false;
		}
		return true;
	}

	private static boolean legalArguments() {
		if (argAmount == 3) {
			return typename.equals(BETWEEN) &&
					isLegalDouble(firstArgument) &&
					isLegalDouble(secondArgument) &&
					Double.parseDouble(firstArgument) < Double.parseDouble(secondArgument);
		} else if (argAmount == 2) {
			switch (typename) {
				case GREATER_THAN:
				case SMALLER_THAN:
					return isLegalDouble(firstArgument);
				case WRITABLE:
				case EXECUTABLE:
				case HIDDEN:
					return firstArgument.equals("YES") || firstArgument.equals("NO");
				case ALL:
				case BETWEEN:
					return false;
				default:
					return true;
			}
		}
		else if (argAmount == 1) {
			return typename.equals(ALL);
		} //TODO speak to mattan
		return false;
	}

	private static boolean isLegalDouble(String str) {
		double test;
		try {
			test = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return test >= 0;
	}


	/**
	 * reset the "NOT" flag and return the filter.
	 * @return
	 */
	public static Filter getFilter() {
		return filter;
	}

	public static String getFirstArgument() {
		return firstArgument;
	}

	public static String getSecondArgument() {
		return secondArgument;
	}

	public static FilterFactory getInstance() {
		return filterFactory;
	}



}
